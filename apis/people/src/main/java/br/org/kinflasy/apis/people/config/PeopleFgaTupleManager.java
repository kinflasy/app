package br.org.kinflasy.apis.people.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.org.kinflasy.libs.api_utils.FgaTupleManager;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.roles.AbilityDto;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PeopleFgaTupleManager extends FgaTupleManager {

    /*
     * Tipos
     */
    private static final String TYPE_USER = "user:";
    private static final String TYPE_CHURCH = "church:";
    private static final String TYPE_PERSON_DATA = "person_data:";
    private static final String TYPE_ADDRESS = "address:";

    /*
     * Relacionamentos
     */
    private static final String RELATION_OWNER = "owner";
    private static final String RELATION_ORIGIN = "origin";

    /*
     * Grupos/Sets
     */
    private static final String SET_UNIT_ADMIN = "#unit_admin";

    @Lazy
    public PeopleFgaTupleManager(final OpenFgaClient client) {
        super(client);
    }

    /**
     * Define o usuário como dono dos seus próprios dados
     * 
     * @param event
     */
    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUserCreated(final EntityEvent.Created<UserDto> event) {
        final var dto = event.getSource();
        final List<ClientTupleKey> tuples = new ArrayList<>();

        final var personDataOwnerTuple = new ClientTupleKey()
                ._object(TYPE_PERSON_DATA + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_USER + dto.getId());
        tuples.add(personDataOwnerTuple);

        Optional.ofNullable(dto.getAddressId())
                .ifPresent(addressId -> {
                    final var addressOriginTuple = new ClientTupleKey()
                            ._object(TYPE_ADDRESS + addressId)
                            .relation(RELATION_ORIGIN)
                            .user(TYPE_PERSON_DATA + dto.getId());
                    tuples.add(addressOriginTuple);
                });

        writeTuples(tuples);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUserDeleted(final EntityEvent.Deleted<UserDto> event) {
        final var dto = event.getSource();

        final var personDataOwnerTuple = new ClientTupleKey()
                ._object(TYPE_PERSON_DATA + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_USER + dto.getId());

        final var addressOriginTuple = new ClientTupleKey()
                ._object(TYPE_ADDRESS + dto.getAddressId())
                .relation(RELATION_ORIGIN)
                .user(TYPE_PERSON_DATA + dto.getId());

        deleteTuples(personDataOwnerTuple, addressOriginTuple);
    }

    /**
     * Define o usuário como dono dos seus próprios dados
     * 
     * @param event
     */
    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleInactivePersonCreated(final EntityEvent.Created<InactivePersonDto> event) {
        final var dto = event.getSource();

        final var personDataOwnerTuple = new ClientTupleKey()
                ._object(TYPE_PERSON_DATA + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_CHURCH + dto.getChurchId() + SET_UNIT_ADMIN);

        final var addressOriginTuple = new ClientTupleKey()
                ._object(TYPE_ADDRESS + dto.getAddressId())
                .relation(RELATION_ORIGIN)
                .user(TYPE_PERSON_DATA + dto.getId());

        writeTuples(personDataOwnerTuple, addressOriginTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleInactivePersonDeleted(final EntityEvent.Deleted<InactivePersonDto> event) {
        final var dto = event.getSource();

        final var personDataOwnerTuple = new ClientTupleKey()
                ._object(TYPE_PERSON_DATA + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_CHURCH + dto.getChurchId() + SET_UNIT_ADMIN);

        final var addressOriginTuple = new ClientTupleKey()
                ._object(TYPE_ADDRESS + dto.getAddressId())
                .relation(RELATION_ORIGIN)
                .user(TYPE_PERSON_DATA + dto.getId());

        deleteTuples(personDataOwnerTuple, addressOriginTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleAbilityCreated(final EntityEvent.Created<AbilityDto> event) {
        final var dto = event.getSource();

        final var abilityTuple = new ClientTupleKey()
                ._object("ability:" + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_USER + dto.getPersonId());

        writeTuples(abilityTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleAbilityDeleted(final EntityEvent.Deleted<AbilityDto> event) {
        final var dto = event.getSource();

        final var abilityTuple = new ClientTupleKey()
                ._object("ability:" + dto.getId())
                .relation(RELATION_OWNER)
                .user(TYPE_USER + dto.getPersonId());

        deleteTuples(abilityTuple);
    }

}
