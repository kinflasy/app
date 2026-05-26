package br.org.kinflasy.apis.calendar.config;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.org.kinflasy.libs.api_utils.FgaTupleManager;
import br.org.kinflasy.libs.calendar.dto.DepartmentCalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.EventCollaborationDto;
import br.org.kinflasy.libs.calendar.dto.UnitCalendarEventDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.apis.calendar.services.CalendarEventService;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CalendarFgaTupleManager extends FgaTupleManager {

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

    private final CalendarEventService service;

    @Lazy
    public CalendarFgaTupleManager(final OpenFgaClient client, final CalendarEventService service) {
        super(client);
        this.service = service;
    }

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

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleEventCollaboratorAdded(
            final EntityEvent.Created<EventCollaborationDto> event) {
        final var dto = event.getSource();

        final var tuple = new ClientTupleKey()
                ._object(TYPE_CALENDAR_EVENT + dto.getCalendarEventId())
                .relation("collaborator")
                .user(TYPE_DEPARTMENT + dto.getDepartmentId());

        return writeTuples(tuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleEventCollaboratorRemoved(
            final EntityEvent.Deleted<EventCollaborationDto> event) {
        final var dto = event.getSource();

        final var tuple = new ClientTupleKey()
                ._object(TYPE_CALENDAR_EVENT + dto.getCalendarEventId())
                .relation("collaborator")
                .user(TYPE_DEPARTMENT + dto.getDepartmentId());

        return deleteTuples(tuple);
    }

}
