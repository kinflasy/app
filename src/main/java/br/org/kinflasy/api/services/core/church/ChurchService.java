package br.org.kinflasy.api.services.core.church;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.repositories.core.church.ChurchRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ChurchService {

    private final ChurchRepository repository;

    public ChurchService(@Autowired final ChurchRepository repository) {
        this.repository = repository;
    }

    public List<Church> findAll() {
        return repository.findAll();
    }

    public Church create(@NonNull final Church church) {
        return repository.save(church);
    }

    public Church findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final Church church) {
        return repository.existsById(church.getId());
    }
    
    public Church update(@NonNull final Church church) throws EntityNotFoundException {
        if (exists(church)) {
            return repository.save(church);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final Church church) throws EntityNotFoundException {
        if (exists(church)) {
            repository.delete(church);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
}
