package br.org.kinflasy.libs.api_utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
@AllArgsConstructor
public abstract class FgaTupleManager {

    protected final OpenFgaClient client;

    @SneakyThrows
    protected CompletableFuture<Void> writeTuples(final List<ClientTupleKey> tuples) {
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

    protected CompletableFuture<Void> writeTuples(final ClientTupleKey... tuples) {
        return writeTuples(List.of(tuples));
    }

    @SneakyThrows
    protected CompletableFuture<Void> deleteTuples(final List<ClientTupleKeyWithoutCondition> tuples) {
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

    protected CompletableFuture<Void> deleteTuples(final ClientTupleKey... tuples) {
        return deleteTuples(List.of(tuples));
    }

}
