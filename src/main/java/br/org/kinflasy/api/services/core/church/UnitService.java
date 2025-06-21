package br.org.kinflasy.api.services.core.church;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.repositories.core.church.UnitRepository;
import br.org.kinflasy.api.services.BaseService;
import br.org.kinflasy.api.services.core.church.department.DepartmentService;

@Service
public class UnitService extends BaseService<UnitRepository, UnitDTO, Unit, Integer> {

    private final DepartmentService departmentService;

    protected UnitService(@Autowired final UnitRepository repository, @Autowired final DepartmentService departmentService) {
        super(repository);
        this.departmentService = departmentService;
    }

    @Override
    public Integer getId(final Unit unit) {
        return unit.getId();
    }

    @Override
    public @Nullable UnitDTO toNullableDTO(final @Nullable Unit unit) {
        return UnitDTO.ofNullable(unit);
    }

    @Override
    public UnitDTO toNonNullDTO(final Unit unit) {
        return UnitDTO.ofNonNull(unit);
    }

    public List<Department> getDepartments(final Integer id) {
        return findById(id).getDepartments();
    }

    public Department createDepartment(final Integer id, final Department department) {
        department.setUnit(findById(id));
        return departmentService.create(department);
    }


}