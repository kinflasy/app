package br.org.kinflasy.apis.auth.migrations.contracts;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.auth.config.OpenFgaConfigManager;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.errors.ApiException;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import dev.openfga.sdk.errors.FgaValidationError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class V2MembershipViewer extends OpenFGAMigration {

    private static final int VERSION = 2;
    private static final String DESCRIPTION = "Adição da permissão de visualização de membresias para gerenciadores de departamentos";

    private final OpenFgaClient client;
    private final OpenFgaConfigManager configManager;

    public V2MembershipViewer(final OpenFgaClient client, final OpenFgaConfigManager configManager) {
        super(VERSION, DESCRIPTION);
        this.client = client;
        this.configManager = configManager;
    }

    @Override
    public void up(final OpenFgaClient client) throws ApiException, FgaInvalidParameterException, FgaValidationError {
        configManager.writeAuthorizationModel();
        configManager.parameterize();
    }

}
