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
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class FgaTupleManager {

    private final OpenFgaClient client;

    /**
     * Define o usuário como dono dos seus próprios dados
     * 
     * @param event
     */
    @Async
    @EventListener
    @SneakyThrows
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreated(final UserCreatedEvent event) {
        final var tuple = new ClientTupleKey()
                .user("user:" + event.getPerson().getId())
                .relation("owner")
                ._object("person_data:" + event.getPerson().getId());

        client.write(new ClientWriteRequest().writes(List.of(tuple)))
                .thenAccept(response -> log.info("Tupla escrita: {}", response))
                .exceptionally(e -> {
                    log.error("Erro ao escrever tupla", e);
                    return null;
                });
    }

}
