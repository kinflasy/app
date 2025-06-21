package br.org.kinflasy.api.services.core.church.department;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.department.DepartmentDTO;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.repositories.core.church.department.DepartmentRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class DepartmentService extends BaseService<DepartmentRepository, DepartmentDTO, Department, UUID> {

    protected DepartmentService(final @Autowired DepartmentRepository repository) {
        super(repository);
    }

    @Override
    public UUID getId(final Department department) {
        return department.getId();
    }

    @Override
    public DepartmentDTO toNullableDTO(final Department department) {
        return DepartmentDTO.ofNullable(department);
    }

    @Override
    public DepartmentDTO toNonNullDTO(final Department department) {
        return DepartmentDTO.ofNonNull(department);
    }

}
