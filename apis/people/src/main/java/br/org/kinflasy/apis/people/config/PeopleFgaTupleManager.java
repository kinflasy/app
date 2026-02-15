package br.org.kinflasy.apis.people.config;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.libs.people.dto.UserDto;
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
public class PeopleFgaTupleManager {

    private static final String TYPE_USER = "user:";
    private static final String TYPE_PERSON_DATA = "person_data:";
    private static final String TYPE_ADDRESS = "address:";

    private static final String RELATION_OWNER = "owner";

    private final OpenFgaClient client;

    /**
     * Define o usuário como dono dos seus próprios dados
     * 
     * @param event
     */
    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreated(final EntityEvent.Created<UserDto> event) {
        final var dto = event.getDto();

        final var personDataOwnerTuple = new ClientTupleKey()
                .user(TYPE_USER + dto.getId())
                .relation(RELATION_OWNER)
                ._object(TYPE_PERSON_DATA + dto.getId());

        final var addressOwnerTuple = new ClientTupleKey()
                .user(TYPE_USER + dto.getId())
                .relation(RELATION_OWNER)
                ._object(TYPE_ADDRESS + dto.getAddressId());

        writeTuples(personDataOwnerTuple, addressOwnerTuple);
    }

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserDeleted(final EntityEvent.Deleted<UserDto> event) {
        final var dto = event.getDto();

        final var personDataOwnerTuple = new ClientTupleKey()
                .user(TYPE_USER + dto.getId())
                .relation(RELATION_OWNER)
                ._object(TYPE_PERSON_DATA + dto.getId());

        final var addressOwnerTuple = new ClientTupleKey()
                .user(TYPE_USER + dto.getId())
                .relation(RELATION_OWNER)
                ._object(TYPE_ADDRESS + dto.getAddressId());

        deleteTuples(personDataOwnerTuple, addressOwnerTuple);
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
