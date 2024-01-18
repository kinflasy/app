package br.org.kinflasy.api.services.core.contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.contact.Address;
import br.org.kinflasy.api.repositories.core.contact.AddressRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(@Autowired final AddressRepository repository) {
        this.repository = repository;
    }

    public List<Address> findAll() {
        return repository.findAll();
    }

    public Address create(@NonNull final Address address) {
        return repository.save(address);
    }

    public Address findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final Address address) {
        return repository.existsById(address.getId());
    }
    
    public Address update(@NonNull final Address address) throws EntityNotFoundException {
        if (exists(address)) {
            return repository.save(address);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final Address address) throws EntityNotFoundException {
        if (exists(address)) {
            repository.delete(address);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }

}
