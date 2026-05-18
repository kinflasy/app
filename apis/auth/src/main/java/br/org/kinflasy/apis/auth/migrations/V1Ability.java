package br.org.kinflasy.apis.auth.migrations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.auth.config.OpenFgaConfigManager;
import br.org.kinflasy.apis.auth.exceptions.OpenFgaMigrationException;
import br.org.kinflasy.apis.auth.migrations.contracts.OpenFGAMigration;
import br.org.kinflasy.apis.auth.repositories.PersonRepository;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientListObjectsRequest;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientWriteRequest;
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
public class V1Ability extends OpenFGAMigration {

    private final OpenFgaConfigManager configManager;
    private final PersonRepository personRepository;

    public V1Ability(final PersonRepository personRepository, final OpenFgaConfigManager configManager) {
        super(1, "Criação do domínio Habilidade, definição de relacionamentos eclesiásticos de Membresias e de Usuários");
        this.personRepository = personRepository;
        this.configManager = configManager;
    }

    @Override
    public void up(final OpenFgaClient client) throws ApiException, FgaInvalidParameterException, FgaValidationError {
        configManager.writeAuthorizationModel();
        configManager.parameterize();
        addMembershipsToUsers(client);
    }

    /**
     * Adiciona os novos relacionamentos de User para usuários pré-existentes
     * 
     * @throws FgaInvalidParameterException
     */
    private void addMembershipsToUsers(final OpenFgaClient client) throws FgaInvalidParameterException {
        final List<ClientTupleKey> tuples = new ArrayList<>();

        personRepository.findAll()
                .forEach(person -> {
                    // Construir request para listar as memberships do usuário
                    final var membershipsRequest = new ClientListObjectsRequest()
                            .type("membership")
                            .relation("user")
                            .user("user:" + person.getId());

                    try {
                        // Executar consulta
                        client.listObjects(membershipsRequest).join().getObjects()

                                // Para cada membership encontrada, criar um tuple de User -> membership
                                .forEach(membership -> tuples.add(new ClientTupleKey()
                                        ._object("user:" + person.getId())
                                        .relation("membership")
                                        .user(membership)));
                    } catch (final FgaInvalidParameterException e) {
                        throw new OpenFgaMigrationException(this, e);
                    }
                });

        if (!tuples.isEmpty()) {
            final var request = new ClientWriteRequest().writes(tuples);
            final var response = client.write(request).join();
            final var status = response.getStatusCode();
            log.info("Status da escrita das tuplas lá: " + status);
        }
    }

}
