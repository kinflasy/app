package br.org.kinflasy.api.dto.core.church;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.org.kinflasy.api.dto.core.church.department.CleanDepartmentDTO;
import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.utils.enums.core.church.UnitType;

public record UnitWithDepartmentsDTO(
        UUID id,
        String name,
        String slug,
        String email,
        String phone,
        UnitType type,
        AddressDTO address,
        List<CleanDepartmentDTO> departments) {

    public static UnitWithDepartmentsDTO ofNullable(final Unit unit) {
        return (unit != null) ? ofNonNull(unit) : null;
    }

    public static UnitWithDepartmentsDTO ofNonNull(final Unit unit) {
        var departments = new ArrayList<>(unit.getDepartments().stream().map(CleanDepartmentDTO::ofNonNull).toList());

        return new UnitWithDepartmentsDTO(unit.getId(), unit.getName(), unit.getSlug(), unit.getEmail(),
                unit.getPhone(), unit.getType(), AddressDTO.ofNullable(unit.getAddress()), departments);
    }

}
