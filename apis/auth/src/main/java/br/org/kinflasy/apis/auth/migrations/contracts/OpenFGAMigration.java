package br.org.kinflasy.apis.auth.migrations.contracts;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.errors.ApiException;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import dev.openfga.sdk.errors.FgaValidationError;

public interface OpenFGAMigration {

    int getVersion();

    /**
     * Descrição legível
     */
    String getDescription();

    /**
     * Executa a migration
     */
    void up(OpenFgaClient fgaClient) throws ApiException, FgaInvalidParameterException, FgaValidationError;

    /**
     * Rollback (opcional, para emergências)
     */
    default void down(final OpenFgaClient fgaClient) throws ApiException, FgaInvalidParameterException, FgaValidationError {
        throw new UnsupportedOperationException("Rollback não implementado");
    }

}
