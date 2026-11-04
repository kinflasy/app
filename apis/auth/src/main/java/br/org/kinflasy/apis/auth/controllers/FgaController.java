package br.org.kinflasy.apis.auth.controllers;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.auth.dto.FgaCheckRequest;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import dev.openfga.OpenFgaExceptionHandler;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCheckRequest;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@RestController
@RequestMapping("v1/core/openfga")
@Tag(name = "Configurações do OpenFGA")
@AllArgsConstructor
public class FgaController {

    private final AuthUtils authUtils;

    private final OpenFgaClient client;
    private final OpenFgaExceptionHandler exceptionHandler;

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

    @GetMapping("check/full")
    @Operation(summary = "Realizar checagem completa de permissão", description = "Realizar uma checagem de permissão considerando o usuário recebido, a relação, o objeto e as características do usuário (gênero e idade).")
    public ResponseEntity<Boolean> fullCheck(final FgaCheckRequest request) {
        final var condition = Map.of("user_gender", request.getUserGender(), "user_age", request.getUserAge());

        final var body = new ClientCheckRequest()
                .user(String.format("%s:%s", request.getUserType(), request.getUserId()))
                .relation(request.getRelation())
                ._object(String.format("%s:%s", request.getObjectType(), request.getObjectId()))
                .context(condition);
        try {
            return ResponseEntity.ok(client.check(body).get().getAllowed());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw exceptionHandler.handle(e, "Error performing FGA check");
        } catch (FgaInvalidParameterException | ExecutionException e) {
            throw exceptionHandler.handle(e, "Error performing FGA check");
        }
    }

    @GetMapping("check")
    @Operation(summary = "Realizar checagem simples de permissão", description = "Realizar uma checagem de permissão considerando o usuário logado.")
    public ResponseEntity<Boolean> simpleCheck(final FgaCheckRequest.Simple request) {
        final var loggedUser = authUtils.getLoggedUser();

        final var fullRequest = FgaCheckRequest.builder()
                .objectType(request.getObjectType())
                .objectId(request.getObjectId())
                .relation(request.getRelation())
                .userType("user")
                .userId(loggedUser.getId().toString())
                .userGender(loggedUser.getGender().name())
                .userAge(loggedUser.getAge())
                .build();

        return fullCheck(fullRequest);
    }

}
