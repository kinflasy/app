package br.org.kinflasy.apis.auth.migrations.contracts;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.errors.ApiException;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import dev.openfga.sdk.errors.FgaValidationError;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OpenFGAMigration {

    private final int version;
    private final String description;

    /**
     * Executa a migration
     */
    public abstract void up(OpenFgaClient client)
            throws ApiException, FgaInvalidParameterException, FgaValidationError;

    /**
     * Rollback (opcional, para emergências)
     */
    public void down(final OpenFgaClient client)
            throws ApiException, FgaInvalidParameterException, FgaValidationError {
        throw new UnsupportedOperationException("Rollback não implementado");
    }

}
