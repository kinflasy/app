package br.org.kinflasy.api.services.core.peopleFilter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.peopleFilter.StaticPeopleFilter;
import br.org.kinflasy.api.repositories.core.peopleFilter.StaticPeopleFilterRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class StaticPeopleFilterService {

    private final StaticPeopleFilterRepository repository;

    public StaticPeopleFilterService(@Autowired final StaticPeopleFilterRepository repository) {
        this.repository = repository;
    }

    public List<StaticPeopleFilter> findAll() {
        return repository.findAll();
    }

    public StaticPeopleFilter create(@NonNull final StaticPeopleFilter staticpeoplefilter) {
        return repository.save(staticpeoplefilter);
    }

    public StaticPeopleFilter findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final StaticPeopleFilter staticpeoplefilter) {
        return repository.existsById(staticpeoplefilter.getId());
    }
    
    public StaticPeopleFilter update(@NonNull final StaticPeopleFilter staticpeoplefilter) throws EntityNotFoundException {
        if (exists(staticpeoplefilter)) {
            return repository.save(staticpeoplefilter);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final StaticPeopleFilter staticpeoplefilter) throws EntityNotFoundException {
        if (exists(staticpeoplefilter)) {
            repository.delete(staticpeoplefilter);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }

}
