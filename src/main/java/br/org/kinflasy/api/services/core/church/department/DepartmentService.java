package br.org.kinflasy.api.services.core.church.department;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.repositories.core.church.department.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(@Autowired final DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> findAll() {
        return repository.findAll();
    }

    public Department create(@NonNull final Department department) {
        return repository.save(department);
    }

    public Department findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final Department department) {
        return repository.existsById(department.getId());
    }
    
    public Department update(@NonNull final Department department) throws EntityNotFoundException {
        if (exists(department)) {
            return repository.save(department);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final Department department) throws EntityNotFoundException {
        if (exists(department)) {
            repository.delete(department);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }

}
