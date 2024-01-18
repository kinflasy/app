package br.org.kinflasy.api.services.core.church.membership;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.church.membership.Leave;
import br.org.kinflasy.api.repositories.core.church.membership.LeaveRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LeaveService {

    private final LeaveRepository repository;

    public LeaveService(@Autowired final LeaveRepository repository) {
        this.repository = repository;
    }

    public List<Leave> findAll() {
        return repository.findAll();
    }

    public Leave create(@NonNull final Leave leave) {
        return repository.save(leave);
    }

    public Leave findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final Leave leave) {
        return repository.existsById(leave.getId());
    }
    
    public Leave update(@NonNull final Leave leave) throws EntityNotFoundException {
        if (exists(leave)) {
            return repository.save(leave);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final Leave leave) throws EntityNotFoundException {
        if (exists(leave)) {
            repository.delete(leave);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }

}
