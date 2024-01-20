package br.org.kinflasy.api.services.core.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.contact.Address;
import br.org.kinflasy.api.repositories.core.contact.AddressRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class AddressService extends BaseService<AddressRepository, AddressDTO, Address, Integer> {

    protected AddressService(@Autowired final AddressRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Integer getId(final @NonNull Address address) {
        return address.getId();
    }

    @Override
    public @Nullable AddressDTO toNullableDTO(final @Nullable Address address) {
        return AddressDTO.ofNullable(address);
    }

    @Override
    public @NonNull AddressDTO toNonNullDTO(final @NonNull Address address) {
        return AddressDTO.ofNonNull(address);
    }

}
