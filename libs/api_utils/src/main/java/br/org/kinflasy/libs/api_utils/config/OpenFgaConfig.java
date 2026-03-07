package br.org.kinflasy.libs.api_utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.api_utils.FgaUtils;
import dev.openfga.OpenFgaExceptionHandler;
import dev.openfga.sdk.api.client.OpenFgaClient;

@Configuration
public class OpenFgaConfig {

    @Bean
    FgaUtils fgau(final OpenFgaClient openFgaClient, final OpenFgaExceptionHandler openFgaExceptionHandler,
            final AuthUtils authUtils) {
        return new FgaUtils(openFgaClient, openFgaExceptionHandler, authUtils);
    }

}
