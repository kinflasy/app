package br.org.kinflasy.api.services.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.repositories.core.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(@Autowired final UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(@NonNull final User user) {
        return repository.save(user);
    }

    public User findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final User user) {
        return repository.existsById(user.getId());
    }
    
    public User update(@NonNull final User user) throws EntityNotFoundException {
        if (exists(user)) {
            return repository.save(user);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final User user) throws EntityNotFoundException {
        if (exists(user)) {
            repository.delete(user);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }

}
