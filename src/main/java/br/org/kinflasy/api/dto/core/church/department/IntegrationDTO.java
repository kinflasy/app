package br.org.kinflasy.api.dto.core.church.department;


import br.org.kinflasy.api.dto.core.InactivePersonDTO;
import br.org.kinflasy.api.entities.core.church.department.Integration;
import br.org.kinflasy.api.utils.enums.core.church.department.IntegrationType;

public record IntegrationDTO(
        Integer id,
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
