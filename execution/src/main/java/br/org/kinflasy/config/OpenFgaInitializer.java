package br.org.kinflasy.config;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.auth.config.OpenFGAMigrationRunner;
import br.org.kinflasy.apis.auth.config.OpenFgaConfigManager;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class OpenFgaInitializer {

    private final OpenFgaConfigManager openFgaConfigManager;
    private final OpenFGAMigrationRunner openFgaMigrationRunner;

    @PostConstruct
    public void init() {
        log.info("Inicializando OpenFGA...");
        openFgaConfigManager.parameterize();
        openFgaMigrationRunner.migrate();
    }

}
