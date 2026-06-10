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
import br.org.kinflasy.libs.calendar.dto.scales.CollaboratorScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.OwnerScaleDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.apis.calendar.repositories.EventCollaborationRepository;
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
    private static final String TYPE_SCALE = "scale:";

    /*
     * Relacionamentos
     */
    private static final String RELATION_OWNER = "owner";
    private static final String RELATION_CALENDAR_EVENT = "calendar_event";

    private final CalendarEventService service;
    private final EventCollaborationRepository collaborationRepository;

    @Lazy
    public CalendarFgaTupleManager(final OpenFgaClient client, final CalendarEventService service,
            final EventCollaborationRepository collaborationRepository) {
        super(client);
        this.service = service;
        this.collaborationRepository = collaborationRepository;
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

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleOwnerScaleCreated(final EntityEvent.Created<OwnerScaleDto> event) {
        final var dto = event.getSource();

        return service.findById(dto.getCalendarEventId())
                .map(calendarEvent -> {
                    // Determinar usuário com base no tipo do evento
                    final var user = switch (calendarEvent) {
                        case UnitCalendarEventDto unitEvent -> TYPE_UNIT + unitEvent.getUnitId();
                        case DepartmentCalendarEventDto deptEvent -> TYPE_DEPARTMENT + deptEvent.getDepartmentId();
                        default -> null;
                    };

                    // Construir tuplas
                    final var ownerTuple = new ClientTupleKey()
                            ._object(TYPE_SCALE + dto.getId())
                            .relation(RELATION_OWNER)
                            .user(user);

                    final var calendarEventTuple = new ClientTupleKey()
                            ._object(TYPE_SCALE + dto.getId())
                            .relation(RELATION_CALENDAR_EVENT)
                            .user(TYPE_CALENDAR_EVENT + dto.getCalendarEventId());

                    // Escrever tuplas
                    return writeTuples(ownerTuple, calendarEventTuple);
                })

                // Se o evento não for encontrado, retornar um futuro concluído
                .orElseGet(() -> CompletableFuture.failedFuture(
                        new IllegalStateException("Evento não encontrado para a escala: " + dto.getCalendarEventId())));
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleOwnerScaleDeleted(final EntityEvent.Deleted<OwnerScaleDto> event) {
        final var dto = event.getSource();

        return service.findById(dto.getCalendarEventId())
                .map(calendarEvent -> {
                    // Determinar usuário com base no tipo do evento
                    final var user = switch (calendarEvent) {
                        case UnitCalendarEventDto unitEvent -> TYPE_UNIT + unitEvent.getUnitId();
                        case DepartmentCalendarEventDto deptEvent -> TYPE_DEPARTMENT + deptEvent.getDepartmentId();
                        default -> null;
                    };

                    // Construir tuplas
                    final var ownerTuple = new ClientTupleKey()
                            ._object(TYPE_SCALE + dto.getId())
                            .relation(RELATION_OWNER)
                            .user(user);

                    final var calendarEventTuple = new ClientTupleKey()
                            ._object(TYPE_SCALE + dto.getId())
                            .relation(RELATION_CALENDAR_EVENT)
                            .user(TYPE_CALENDAR_EVENT + dto.getCalendarEventId());

                    // Deletar tuplas
                    return deleteTuples(ownerTuple, calendarEventTuple);
                })

                // Se o evento não for encontrado, retornar um futuro concluído
                .orElseGet(() -> CompletableFuture.failedFuture(
                        new IllegalStateException("Evento não encontrado para a escala: " + dto.getCalendarEventId())));
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleCollaboratorScaleCreated(
            final EntityEvent.Created<CollaboratorScaleDto> event) {
        final var dto = event.getSource();

        return collaborationRepository.findById(dto.getCollaborationId())
                .map(collab -> {
                    // Construir tuplas
                    final var ownerTuple = new ClientTupleKey()
                            ._object(TYPE_SCALE + dto.getId())
                            .relation(RELATION_OWNER)
                            .user(TYPE_DEPARTMENT + collab.getDepartmentId());

                    final var calendarEventTuple = new ClientTupleKey()
                            ._object(TYPE_SCALE + dto.getId())
                            .relation(RELATION_CALENDAR_EVENT)
                            .user(TYPE_CALENDAR_EVENT + collab.getCalendarEventId());

                    // Escrever tuplas
                    return writeTuples(ownerTuple, calendarEventTuple);
                })

                // Se o evento não for encontrado, retornar um futuro concluído
                .orElseGet(() -> CompletableFuture.failedFuture(
                        new IllegalStateException(
                                "Evento não encontrado para a colaboração: " + dto.getCollaborationId())));
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public CompletableFuture<Void> handleCollaboratorScaleDeleted(
            final EntityEvent.Deleted<CollaboratorScaleDto> event) {
        final var dto = event.getSource();

        return collaborationRepository.findById(dto.getCollaborationId())
                .map(collab -> {
                    // Construir tuplas
                    final var ownerTuple = new ClientTupleKey()
                            ._object(TYPE_SCALE + dto.getId())
                            .relation(RELATION_OWNER)
                            .user(TYPE_DEPARTMENT + collab.getDepartmentId());

                    final var calendarEventTuple = new ClientTupleKey()
                            ._object(TYPE_SCALE + dto.getId())
                            .relation(RELATION_CALENDAR_EVENT)
                            .user(TYPE_CALENDAR_EVENT + collab.getCalendarEventId());

                    // Escrever tuplas
                    return deleteTuples(ownerTuple, calendarEventTuple);
                })

                // Se o evento não for encontrado, retornar um futuro concluído
                .orElseGet(() -> CompletableFuture.failedFuture(
                        new IllegalStateException(
                                "Evento não encontrado para a colaboração: " + dto.getCollaborationId())));
    }

}
