package br.org.kinflasy.api.services.core.church;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.repositories.core.church.UnitRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UnitService {

    private final UnitRepository repository;

    public UnitService(@Autowired final UnitRepository repository) {
        this.repository = repository;
    }

    public List<Unit> findAll() {
        return repository.findAll();
    }

    public Unit create(@NonNull final Unit unit) {
        return repository.save(unit);
    }

    public Unit findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final Unit unit) {
        return repository.existsById(unit.getId());
    }
    
    public Unit update(@NonNull final Unit unit) throws EntityNotFoundException {
        if (exists(unit)) {
            return repository.save(unit);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final Unit unit) throws EntityNotFoundException {
        if (exists(unit)) {
            repository.delete(unit);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }

}
