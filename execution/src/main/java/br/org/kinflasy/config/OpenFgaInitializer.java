package br.org.kinflasy.config;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCreateStoreResponse;
import dev.openfga.sdk.api.model.CreateStoreRequest;
import dev.openfga.sdk.api.model.WriteAuthorizationModelRequest;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OpenFgaInitializer {

    private final String modelFilePath;

    private final OpenFgaClient client;
    private final ObjectMapper objectMapper;

    public OpenFgaInitializer(
            @Value("${app.openfga.model-file-path}") final String modelFilePath,
            final OpenFgaClient client,
            final ObjectMapper objectMapper) {
        this.modelFilePath = modelFilePath;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        log.info("Inicializando OpenFGA...");

        try {
            // 1. Verificar/Criar Store
            final var storeId = findOrCreateStoreId();
            client.setStoreId(storeId);

            // 2. Verificar/Criar Authorization Model
            final var authorizationModelId = findOrCreateAuthorizationModelId();
            client.setAuthorizationModelId(authorizationModelId);
        } catch (final Exception e) {
            log.error("Erro ao initializar OpenFGA", e);
            throw new RuntimeException("Falha na inicialização do OpenFGA", e);
        }
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

    @SneakyThrows
    private String createAuthorizationModel() {
        final var request = objectMapper.readValue(new File(modelFilePath), WriteAuthorizationModelRequest.class);
        client.writeAuthorizationModel(request)
                .join();
        return null;
    }
}