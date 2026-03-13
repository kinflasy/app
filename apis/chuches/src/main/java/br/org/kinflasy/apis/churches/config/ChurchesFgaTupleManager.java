package br.org.kinflasy.apis.churches.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.events.department.ExtensionEvent;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientTupleKeyWithoutCondition;
import dev.openfga.sdk.api.configuration.ClientDeleteTuplesOptions;
import dev.openfga.sdk.api.configuration.ClientWriteTuplesOptions;
import dev.openfga.sdk.api.model.WriteRequestDeletes.OnMissingEnum;
import dev.openfga.sdk.api.model.WriteRequestWrites.OnDuplicateEnum;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class ChurchesFgaTupleManager {

    /*
     * Constantes de tipos
     */
    private static final String TYPE_PERSON_DATA = "person_data:";
    private static final String TYPE_USER = "user:";
    private static final String TYPE_CHURCH = "church:";
    private static final String TYPE_UNIT = "unit:";
    private static final String TYPE_ADDRESS = "address:";
    private static final String TYPE_DEPARTMENT = "department:";
    private static final String TYPE_MEMBERSHIP = "membership:";

    /*
     * Constantes de sets
     */
    private static final String SET_USER = "#user";
    private static final String SET_ADMIN = "#admin";

    private final OpenFgaClient client;

    private final DepartmentService departmentService;

    private final ChurchesFgaTupleManager tupleManager;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleUnitCreated(final EntityEvent.Created<UnitDto> event) {
        final var dto = event.getSource();

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
                .relation("origin")
                .user(TYPE_UNIT + dto.getId());

        tuples.add(parentChurchTuple);
        tuples.add(unitTuple);
        tuples.add(addressOwnerTuple);

        if (UnitType.MAIN.equals(dto.getType())) {
            final var mainUnitTuple = new ClientTupleKey()
                    ._object(TYPE_CHURCH + dto.getChurchId())
                    .relation("admin")
                    .user(TYPE_UNIT + dto.getId() + SET_ADMIN);

            tuples.add(mainUnitTuple);
        }

        return writeTuples(tuples);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleDepartmentCreated(final EntityEvent.Created<DepartmentDto> event) {
        final var dto = event.getSource();

        final var parentUnitTuple = new ClientTupleKey()
                ._object(TYPE_DEPARTMENT + dto.getId())
                .relation("parent_unit")
                .user(TYPE_UNIT + dto.getUnitId());

        final var departmentTuple = new ClientTupleKey()
                ._object(TYPE_UNIT + dto.getUnitId())
                .relation("department")
                .user(TYPE_DEPARTMENT + dto.getId());

        return writeTuples(parentUnitTuple, departmentTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleMembershipCreated(final EntityEvent.Created<MembershipDto> event) {
        final var dto = event.getSource();

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
                .user(TYPE_MEMBERSHIP + dto.getId() + SET_USER);

        final var viewPersonDataTuple = new ClientTupleKey()
                ._object(TYPE_PERSON_DATA + dto.getPerson().getId())
                .relation("can_view")
                .user(TYPE_MEMBERSHIP + dto.getId() + "#can_edit");

        return writeTuples(userTuple, unitTuple, unitMembershipTuple, viewPersonDataTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleMembershipDeleted(final EntityEvent.Deleted<MembershipDto> event) {
        final var dto = event.getSource();

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
                .user(TYPE_MEMBERSHIP + dto.getId() + SET_USER);

        final var viewPersonDataTuple = new ClientTupleKey()
                ._object(TYPE_PERSON_DATA + dto.getPerson().getId())
                .relation("can_view")
                .user(TYPE_MEMBERSHIP + dto.getId() + "#can_edit");

        return deleteTuples(userTuple, unitTuple, unitMembershipTuple, viewPersonDataTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleMembershipUpdated(final EntityEvent.Updated<MembershipDto> event) {
        // Deletar tuplas originais da membresia
        return tupleManager.handleMembershipDeleted(new EntityEvent.Deleted<>(event.getOriginal()))

                // Escrever tuplas modificadas
                .thenCompose(ignored -> tupleManager
                        .handleMembershipCreated(new EntityEvent.Created<>(event.getSource())));
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleIntegrationCreated(final EntityEvent.Created<IntegrationDto> event) {
        final var dto = event.getSource();

        final var parentUnitTuple = new ClientTupleKey()
                ._object(TYPE_DEPARTMENT + dto.getDepartmentId())
                .relation(dto.getType().name().toLowerCase())
                .user(TYPE_MEMBERSHIP + dto.getMembershipId() + SET_USER);

        return writeTuples(parentUnitTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handleExtensionSubscribed(
            final ExtensionEvent.Subscribed<ExtensionSubscriptionDto> event) {
        final var dto = event.getSource();

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
    private CompletableFuture<Void> writeTuples(final List<ClientTupleKey> tuples) {
        return client.writeTuples(tuples, new ClientWriteTuplesOptions().onDuplicate(OnDuplicateEnum.IGNORE))
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

    private CompletableFuture<Void> writeTuples(final ClientTupleKey... tuples) {
        return writeTuples(List.of(tuples));
    }

    @SneakyThrows
    private CompletableFuture<Void> deleteTuples(final List<ClientTupleKeyWithoutCondition> tuples) {
        return client.deleteTuples(tuples, new ClientDeleteTuplesOptions().onMissing(OnMissingEnum.IGNORE))
                .thenAccept(response -> {
                    final var deletes = response.getDeletes();
                    log.info("{} tuplas deletadas: {}", deletes.size(),
                            deletes.stream()
                                    .map(delete -> delete.getTupleKey())
                                    .reduce("",
                                            (acc, tuple) -> "%s\n%s %s %s (%s)".formatted(acc, tuple.getUser(),
                                                    tuple.getRelation(), tuple.getObject(), tuple.getCondition()),
                                            (a, b) -> a + b));
                })
                .exceptionally(e -> {
                    log.error("Erro ao deletar tuplas", e);
                    return null;
                });
    }

    private CompletableFuture<Void> deleteTuples(final ClientTupleKey... tuples) {
        return deleteTuples(List.of(tuples));
    }

}
