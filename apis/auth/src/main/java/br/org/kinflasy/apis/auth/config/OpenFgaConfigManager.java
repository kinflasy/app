package br.org.kinflasy.apis.auth.config;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.kinflasy.apis.auth.exceptions.OpenFgaParameterizationException;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCreateStoreResponse;
import dev.openfga.sdk.api.model.CreateStoreRequest;
import dev.openfga.sdk.api.model.WriteAuthorizationModelRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OpenFgaConfigManager {

    private final String modelFilePath;

    private final OpenFgaClient client;
    private final ObjectMapper objectMapper;

    public OpenFgaConfigManager(@Value("${app.openfga.model-file-path}") final String modelFilePath,
            final OpenFgaClient client, final ObjectMapper objectMapper) {
        this.modelFilePath = modelFilePath;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void parameterize() {
        log.info("Parametrizando client do OpenFGA...");

        try {
            // 1. Verificar/Criar Store
            final var storeId = findOrCreateStoreId();
            client.setStoreId(storeId);

            // 2. Verificar/Criar Authorization Model
            final var authorizationModelId = findOrCreateAuthorizationModelId();
            client.setAuthorizationModelId(authorizationModelId);
        } catch (final Exception e) {
            log.error("Erro ao parametrizar client do OpenFGA", e);
            throw new OpenFgaParameterizationException("Falha na parametrização do OpenFGA", e);
        }
    }

    @SneakyThrows
    public String createAuthorizationModel() {
        final var request = objectMapper.readValue(new File(modelFilePath), WriteAuthorizationModelRequest.class);
        final var response = client.writeAuthorizationModel(request)
                .join();
        return response.getAuthorizationModelId();
    }

    @SneakyThrows
    private String findOrCreateStoreId() {
        // Listar stores existentes
        return client.listStores().join()
                .getStores().stream()

                // Usar a primeira, se existir
                .findFirst()
                .map(store -> {
                    final var storeId = store.getId();
                    log.info("Obtida store já existente: {}", storeId);
                    return storeId;
                })

                // Se não existir, criar nova store
                .orElseGet(() -> {
                    final var store = createStore("KinflasyStore");
                    final var storeId = store.getId();
                    log.info("Store criada: {}", storeId);
                    return storeId;
                });
    }

    @SneakyThrows
    private ClientCreateStoreResponse createStore(final String storeName) {
        return client.createStore(new CreateStoreRequest()
                .name(storeName))
                .join();
    }

    @SneakyThrows
    private String findOrCreateAuthorizationModelId() {
        return Optional.ofNullable(client.readLatestAuthorizationModel().join().getAuthorizationModel())
                .map(model -> {
                    final var modelId = model.getId();
                    log.info("Obtido authorization model mais recente: {}", modelId);
                    return modelId;
                })
                .orElseGet(() -> {
                    // Criar novo model a partir do model.json
                    final var modelId = createAuthorizationModel();
                    log.info("Authorization Model criada: {}", modelId);
                    return modelId;
                });
    }

}
