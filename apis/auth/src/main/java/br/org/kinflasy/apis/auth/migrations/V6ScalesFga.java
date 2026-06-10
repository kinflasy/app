package br.org.kinflasy.apis.auth.migrations;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.auth.config.OpenFgaConfigManager;
import br.org.kinflasy.apis.auth.migrations.contracts.SimpleReparameterizationOpenFgaMigration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class V6ScalesFga extends SimpleReparameterizationOpenFgaMigration {

    private static final int VERSION = 6;
    private static final String DESCRIPTION = "Correção das escalas no FGA";

    public V6ScalesFga(final OpenFgaConfigManager configManager) {
        super(configManager, VERSION, DESCRIPTION);
    }

}
