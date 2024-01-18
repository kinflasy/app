package br.org.kinflasy.api.services.core.church.membership;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.church.membership.Entry;
import br.org.kinflasy.api.repositories.core.church.membership.EntryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EntryService {

    private final EntryRepository repository;

    public EntryService(@Autowired final EntryRepository repository) {
        this.repository = repository;
    }

    public List<Entry> findAll() {
        return repository.findAll();
    }

    public Entry create(@NonNull final Entry entry) {
        return repository.save(entry);
    }

    public Entry findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final Entry entry) {
        return repository.existsById(entry.getId());
    }
    
    public Entry update(@NonNull final Entry entry) throws EntityNotFoundException {
        if (exists(entry)) {
            return repository.save(entry);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final Entry entry) throws EntityNotFoundException {
        if (exists(entry)) {
            repository.delete(entry);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }

}
