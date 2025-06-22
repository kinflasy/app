package br.org.kinflasy.api.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.churches.entities.Unit;
import br.org.kinflasy.api.apis.churches.entities.department.Department;
import br.org.kinflasy.api.apis.churches.repositories.UnitRepository;
import br.org.kinflasy.api.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.services.BaseService;

@Service
public class UnitService extends BaseService<UnitRepository, UnitDTO, Unit, UUID> {

    private final DepartmentService departmentService;

    protected UnitService(@Autowired final UnitRepository repository, @Autowired final DepartmentService departmentService) {
        super(repository);
        this.departmentService = departmentService;
    }

    @Override
    public UUID getId(final Unit unit) {
        return unit.getId();
    }

    @Override
    public UnitDTO toDto(final Unit unit) {
        return UnitDTO.ofNonNull(unit);
    }

    public List<Department> getDepartments(final UUID id) {
        return findById(id).getDepartments();
    }

    public Department createDepartment(final UUID id, final Department department) {
        department.setUnit(findById(id));
        return departmentService.create(department);
    }


}