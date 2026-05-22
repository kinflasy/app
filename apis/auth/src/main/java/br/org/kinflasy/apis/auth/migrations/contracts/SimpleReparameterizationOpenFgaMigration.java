package br.org.kinflasy.apis.auth.migrations.contracts;

import br.org.kinflasy.apis.auth.config.OpenFgaConfigManager;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.errors.ApiException;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import dev.openfga.sdk.errors.FgaValidationError;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class SimpleReparameterizationOpenFgaMigration extends OpenFGAMigration {

    private final OpenFgaConfigManager configManager;

    protected SimpleReparameterizationOpenFgaMigration(final OpenFgaConfigManager configManager, final int version,
            final String description) {
        super(version, description);
        this.configManager = configManager;
    }

    @Override
    public void up(OpenFgaClient client) throws ApiException, FgaInvalidParameterException, FgaValidationError {
        configManager.writeAuthorizationModel();
        configManager.parameterize();
    }

}
