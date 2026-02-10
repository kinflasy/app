package br.org.kinflasy.apis.people.config;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.org.kinflasy.libs.people.events.UserCreatedEvent;
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
public class PeopleFgaTupleManager {

    private final OpenFgaClient client;

    /**
     * Define o usuário como dono dos seus próprios dados
     * 
     * @param event
     */
    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreated(final UserCreatedEvent event) {
        final var user = event.getPerson();

        final var personDataOwnerTuple = new ClientTupleKey()
                .user("user:" + user.getId())
                .relation("owner")
                ._object("person_data:" + user.getId());

        final var addressOwnerTuple = new ClientTupleKey()
                .user("user:" + user.getId())
                .relation("owner")
                ._object("address:" + user.getAddressId());

        writeTuples(personDataOwnerTuple, addressOwnerTuple);
    }

    @SneakyThrows
    private void writeTuples(final ClientTupleKey... tuples) {
        client.write(new ClientWriteRequest().writes(List.of(tuples)),
                new ClientWriteOptions().onDuplicate(OnDuplicateEnum.IGNORE))
                .thenAccept(response -> log.info("Tuplas escritas: {}", response))
                .exceptionally(e -> {
                    log.error("Erro ao escrever tuplas", e);
                    return null;
                });
    }

}
