package br.org.kinflasy.libs.api_utils;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.modelmapper.ModelMapper;

import br.org.kinflasy.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.PersonIdentifierDto;
import dev.openfga.OpenFgaExceptionHandler;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCheckRequest;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FgaUtils {

    private final ModelMapper mapper;

    private final OpenFgaClient fgaClient;
    private final OpenFgaExceptionHandler exceptionHandler;

    private final AuthUtils authUtils;

    public boolean withCharacteristics(final String objectType, final Object objectId, final String relation) {
        final var loggedUser = authUtils.getLoggedUser();

        return withCharacteristics(objectType, objectId, relation, "user", loggedUser.getId().toString(),
                loggedUser);
    }

    public boolean withCharacteristics(final String objectType, final Object objectId, final String relation,
            final String userType, final String userId, final PersonDto person) {
        final var condition = Map.of("user_gender", person.getGender(), "user_age", person.getAge());

        final var body = new ClientCheckRequest()
                .user(String.format("%s:%s", userType, userId))
                .relation(relation)
                ._object(String.format("%s:%s", objectType, objectId))
                .context(condition);
        try {
            return Boolean.TRUE.equals(fgaClient.check(body).get().getAllowed());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw exceptionHandler.handle(e, "Error performing FGA check");
        } catch (FgaInvalidParameterException | ExecutionException e) {
            throw exceptionHandler.handle(e, "Error performing FGA check");
        }
    }

    public boolean withCharacteristics(final String objectType, final Object objectId, final String relation,
            final PersonDto person) {
        return withCharacteristics(objectType, objectId, relation, "user", person.getId().toString(), person);
    }

    public boolean withCharacteristics(final String objectType, final Object objectId, final String relation,
            final String userType, final String userId, final PersonIdentifierDto identifier) {
        final var dto = mapper.map(identifier, InactivePersonDto.class);
        return withCharacteristics(objectType, objectId, relation, userType, userId, dto);
    }

    public boolean withCharacteristics(final String objectType, final Object objectId, final String relation,
            final PersonIdentifierDto identifier) {
        return withCharacteristics(objectType, objectId, relation, "user", identifier.getId().toString(), identifier);
    }

}
