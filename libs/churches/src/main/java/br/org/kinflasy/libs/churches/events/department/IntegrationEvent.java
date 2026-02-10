package br.org.kinflasy.libs.churches.events.department;

import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import lombok.Data;

public interface IntegrationEvent {

    public IntegrationDto getIntegration();

    @Data
    public static final class Created implements IntegrationEvent {
        private final IntegrationDto integration;
    }

}
