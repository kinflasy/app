package br.org.kinflasy.apis.auth.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.openfga.sdk.api.client.OpenFgaClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@RestController
@RequestMapping("v1/core/openfga")
@Tag(name = "Configurações do OpenFGA")
@AllArgsConstructor
public class FgaController {

    private final OpenFgaClient client;

    @SneakyThrows
    @GetMapping("config")
    @Operation(summary = "Obter configurações vigentes do OpenFGA", description = "Obter as configurações necessárias para interagir com o OpenFGA, como storeId e authorizationModelId.")
    public ResponseEntity<Map<String, String>> getConfig() {
        final var storeId = client.getStore().join().getId();
        final var authorizationModelId = client.readAuthorizationModel().join().getAuthorizationModel().getId();

        return ResponseEntity.ok(Map.of(
                "storeId", storeId,
                "authorizationModelId", authorizationModelId));
    }

}
