package br.org.kinflasy.api.dto.core.church.department;

import java.util.UUID;

import br.org.kinflasy.api.apis.churches.entities.department.Integration;
import br.org.kinflasy.api.dto.core.InactivePersonDTO;
import br.org.kinflasy.api.libs.churches.enums.department.IntegrationType;

public record IntegrationDTO(
        UUID id,
        DepartmentDTO department,
        InactivePersonDTO person,
        IntegrationType type) {

    public static IntegrationDTO ofNullable(final Integration integration) {
        return (integration != null) ? ofNonNull(integration) : null;
    }

    public static IntegrationDTO ofNonNull(final Integration integration) {
        return new IntegrationDTO(integration.getId(), DepartmentDTO.ofNonNull(integration.getDepartment()),
                InactivePersonDTO.ofNonNull(integration.getPerson()), integration.getType());
    }

}
