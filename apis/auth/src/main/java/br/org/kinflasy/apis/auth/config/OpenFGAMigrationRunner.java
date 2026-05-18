package br.org.kinflasy.apis.auth.config;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.auth.dto.OpenFgaMigrationStatus;
import br.org.kinflasy.apis.auth.entities.OpenFgaMigrationLog;
import br.org.kinflasy.apis.auth.exceptions.OpenFgaMigrationException;
import br.org.kinflasy.apis.auth.migrations.contracts.OpenFGAMigration;
import br.org.kinflasy.apis.auth.repositories.MigrationLogRepository;
import dev.openfga.sdk.api.client.OpenFgaClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OpenFGAMigrationRunner {

    private final OpenFgaClient client;
    private final MigrationLogRepository repository;
    private final List<OpenFGAMigration> migrations;

    public OpenFGAMigrationRunner(final OpenFgaClient client, final MigrationLogRepository repository,
            final List<OpenFGAMigration> migrations) {
        this.client = client;
        this.repository = repository;

        // Ordena por versão automaticamente
        this.migrations = migrations.stream()
                .sorted(Comparator.comparingInt(OpenFGAMigration::getVersion))
                .toList();
    }

    public void migrate() {
        log.info("Iniciando OpenFGA migrations...");

        migrations.stream()

                // Pular migrations já executadas com sucesso
                .filter(migration -> {
                    final var alreadyExecuted = repository.existsByVersionAndStatus(migration.getVersion(),
                            OpenFgaMigrationStatus.SUCCESS);

                    if (alreadyExecuted) {
                        log.info("Migration V{} já foi executada, pulando", migration.getVersion());
                    }

                    return !alreadyExecuted;
                })

                // Executar as migrations pendentes
                .forEach(migration -> {
                    // Preparar log da migration
                    final var migrationLog = new OpenFgaMigrationLog();
                    migrationLog.setVersion(migration.getVersion());
                    migrationLog.setDescription(migration.getDescription());
                    migrationLog.setExecutedAt(LocalDateTime.now());

                    try {
                        log.info("Executando V{}: {}...", migration.getVersion(), migration.getDescription());

                        // Executar
                        migration.up(client);

                        // Registrar sucesso
                        migrationLog.setStatus(OpenFgaMigrationStatus.SUCCESS);
                        log.info("✅ V{} executada com sucesso", migration.getVersion());
                    } catch (final Exception e) {
                        // Registrar falha
                        migrationLog.setStatus(OpenFgaMigrationStatus.FAILED);
                        log.error("❌ Erro na V{}: {}", migration.getVersion(), e.getMessage(), e);

                        // Parar execução em caso de erro crítico
                        throw new OpenFgaMigrationException(migration, e);
                    } finally {
                        // Salvar log da migration
                        repository.save(migrationLog);
                    }
                });

        log.info("✅ Todas as OpenFGA migrations completadas!");
    }

}
