package br.org.kinflasy.config;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.org.kinflasy.dto.DepartmentCalendarEventDto;
import br.org.kinflasy.dto.UnitCalendarEventDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.services.CalendarEventService;
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
@AllArgsConstructor
public class CalendarFgaTupleManager {

    /*
     * Tipos
     */
    private static final String TYPE_CALENDAR_EVENT = "calendar_event:";
    private static final String TYPE_UNIT = "unit:";
    private static final String TYPE_DEPARTMENT = "department:";

    /*
     * Relacionamentos
     */
    private static final String RELATION_OWNER = "owner";

    private final OpenFgaClient client;

    private final CalendarEventService service;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleUnitCalendarEventCreated(
            final EntityEvent.Created<UnitCalendarEventDto> event) {
        final var dto = event.getSource();

        final var tuple = new ClientTupleKey()
                ._object(TYPE_CALENDAR_EVENT + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_UNIT + dto.getUnitId());

        return writeTuples(tuple)
                .thenRun(() -> service.postCreate(dto));
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleDepartmentCalendarEventCreated(
            final EntityEvent.Created<DepartmentCalendarEventDto> event) {
        final var dto = event.getSource();

        final var tuple = new ClientTupleKey()
                ._object(TYPE_CALENDAR_EVENT + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_DEPARTMENT + dto.getDepartmentId());

        return writeTuples(tuple)
                .thenRun(() -> service.postCreate(dto));
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleUnitCalendarEventDeleted(
            final EntityEvent.Deleted<UnitCalendarEventDto> event) {
        final var dto = event.getSource();

        final var tuple = new ClientTupleKey()
                ._object(TYPE_CALENDAR_EVENT + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_UNIT + dto.getUnitId());

        return deleteTuples(tuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleDepartmentCalendarEventDeleted(
            final EntityEvent.Deleted<DepartmentCalendarEventDto> event) {
        final var dto = event.getSource();

        final var tuple = new ClientTupleKey()
                ._object(TYPE_CALENDAR_EVENT + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_DEPARTMENT + dto.getDepartmentId());

        return deleteTuples(tuple);
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
