package br.org.kinflasy.apis.churches.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.events.MembershipEvent;
import br.org.kinflasy.libs.churches.events.UnitEvent;
import br.org.kinflasy.libs.churches.events.department.DepartmentEvent;
import br.org.kinflasy.libs.churches.events.department.ExtensionEvent;
import br.org.kinflasy.libs.churches.events.department.IntegrationEvent;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientWriteRequest;
import dev.openfga.sdk.api.configuration.ClientWriteOptions;
import dev.openfga.sdk.api.model.WriteRequestWrites.OnDuplicateEnum;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ChurchesFgaTupleManager {

    private static final String TYPE_USER = "user:";
    private static final String TYPE_CHURCH = "church:";
    private static final String TYPE_UNIT = "unit:";
    private static final String TYPE_ADDRESS = "address:";
    private static final String TYPE_DEPARTMENT = "department:";
    private static final String TYPE_MEMBERSHIP = "membership:";

    private final OpenFgaClient client;

    private final DepartmentService departmentService;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleUnitCreated(final UnitEvent.Created event) {
        final var dto = event.getUnit();

        final List<ClientTupleKey> tuples = new ArrayList<>();

        final var parentChurchTuple = new ClientTupleKey()
                ._object(TYPE_UNIT + dto.getId())
                .relation("parent_church")
                .user(TYPE_CHURCH + dto.getChurchId());

        final var unitTuple = new ClientTupleKey()
                ._object(TYPE_CHURCH + dto.getChurchId())
                .relation("unit")
                .user(TYPE_UNIT + dto.getId());

        final var addressOwnerTuple = new ClientTupleKey()
                ._object(TYPE_ADDRESS + dto.getAddressId())
                .relation("owner")
                .user(TYPE_UNIT + dto.getId());

        tuples.add(parentChurchTuple);
        tuples.add(unitTuple);
        tuples.add(addressOwnerTuple);

        if (UnitType.MAIN.equals(dto.getType())) {
            final var mainUnitTuple = new ClientTupleKey()
                    ._object(TYPE_CHURCH + dto.getChurchId())
                    .relation("admin")
                    .user(TYPE_UNIT + dto.getId() + "#admin");

            tuples.add(mainUnitTuple);
        }

        return writeTuples(tuples);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleDepartmentCreated(final DepartmentEvent.Created event) {
        final var dto = event.getDepartment();

        final var parentUnitTuple = new ClientTupleKey()
                ._object(TYPE_DEPARTMENT + dto.getId())
                .relation("parent_unit")
                .user(TYPE_UNIT + dto.getUnitId());

        return writeTuples(parentUnitTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleMembershipCreated(final MembershipEvent.Created event) {
        final var dto = event.getMembership();

        final var userTuple = new ClientTupleKey()
                ._object(TYPE_MEMBERSHIP + dto.getId())
                .relation("user")
                .user(TYPE_USER + dto.getPerson().getId());

        final var unitTuple = new ClientTupleKey()
                ._object(TYPE_MEMBERSHIP + dto.getId())
                .relation("unit")
                .user(TYPE_UNIT + dto.getUnitId());

        final var unitMembershipTuple = new ClientTupleKey()
                ._object(TYPE_UNIT + dto.getUnitId())
                .relation(dto.getAffiliation().name().toLowerCase())
                .user(TYPE_MEMBERSHIP + dto.getId() + "#user");

        return writeTuples(userTuple, unitTuple, unitMembershipTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleIntegrationCreated(final IntegrationEvent.Created event) {
        final var dto = event.getIntegration();

        final var parentUnitTuple = new ClientTupleKey()
                ._object(TYPE_DEPARTMENT + dto.getDepartmentId())
                .relation(dto.getType().name().toLowerCase())
                .user(TYPE_MEMBERSHIP + dto.getMembershipId() + "#user");

        return writeTuples(parentUnitTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleExtensionSubscribed(final ExtensionEvent.Subscribed event) {
        final var dto = event.getSubscription();

        return departmentService.findById(dto.getDepartmentId())
                .map(department -> {
                    final List<ClientTupleKey> tuples = new ArrayList<>();

                    if (Extension.SOMA.equals(dto.getExtension())) {
                        final var unitAdminDepartmentTuple = new ClientTupleKey()
                                ._object(TYPE_UNIT + department.getUnitId())
                                .relation("admin")
                                .user(TYPE_DEPARTMENT + department.getId() + "#leader");

                        tuples.add(unitAdminDepartmentTuple);
                    }

                    return writeTuples(tuples);
                })
                .orElseGet(() -> new CompletableFuture<>());
    }

    @SneakyThrows
    private CompletableFuture<Void> writeTuples(final ClientTupleKey... tuples) {
        return client.write(new ClientWriteRequest().writes(List.of(tuples)),
                new ClientWriteOptions().onDuplicate(OnDuplicateEnum.IGNORE))
                .thenAccept(response -> {
                    final var writes = response.getWrites();
                    log.info("{} tuplas escritas: {}", writes.size(),
                            writes.stream()
                                    .map(write -> write.getTupleKey())
                                    .reduce("",
                                            (acc, tuple) -> "%s\n%s %s %s (%s)".formatted(acc, tuple.getUser(),
                                                    tuple.getRelation(), tuple.getObject(), tuple.getCondition()),
                                            (a, b) -> a + b));
                })
                .exceptionally(e -> {
                    log.error("Erro ao escrever tuplas", e);
                    return null;
                });
    }

    private CompletableFuture<Void> writeTuples(final Collection<ClientTupleKey> tuples) {
        return writeTuples(tuples.toArray(new ClientTupleKey[0]));
    }

}
