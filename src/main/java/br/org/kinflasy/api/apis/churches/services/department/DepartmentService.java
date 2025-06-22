package br.org.kinflasy.api.apis.churches.services.department;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.churches.entities.department.Department;
import br.org.kinflasy.api.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.api.dto.core.church.department.DepartmentDTO;
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
    public DepartmentDTO toDto(final Department department) {
        return DepartmentDTO.ofNonNull(department);
    }

}
