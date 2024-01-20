package br.org.kinflasy.api.services.core.church.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.department.DepartmentDTO;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.repositories.core.church.department.DepartmentRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class DepartmentService extends BaseService<DepartmentRepository, Object, Department, Integer> {

    protected DepartmentService(final @Autowired DepartmentRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Integer getId(final @NonNull Department department) {
        return department.getId();
    }

    @Override
    public @Nullable Object toNullableDTO(final @Nullable Department department) {
        return DepartmentDTO.ofNullable(department);
    }

    @Override
    public @NonNull Object toNonNullDTO(final @NonNull Department department) {
        return DepartmentDTO.ofNonNull(department);
    }

}
