package br.org.kinflasy.libs.churches.events.department;

import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import lombok.Data;

@Data
public abstract class IntegrationEvent {

    private final IntegrationDto integration;

    public static final class Created extends IntegrationEvent {
        public Created(final IntegrationDto integration) {
            super(integration);
        }
    }

}
