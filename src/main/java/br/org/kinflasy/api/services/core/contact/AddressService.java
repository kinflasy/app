package br.org.kinflasy.api.services.core.contact;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.contact.Address;
import br.org.kinflasy.api.repositories.core.contact.AddressRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class AddressService extends BaseService<AddressRepository, AddressDTO, Address, UUID> {

    protected AddressService(@Autowired final AddressRepository repository) {
        super(repository);
    }

    @Override
    public UUID getId(final Address address) {
        return address.getId();
    }

    @Override
    public AddressDTO toNullableDTO(final Address address) {
        return AddressDTO.ofNullable(address);
    }

    @Override
    public AddressDTO toNonNullDTO(final Address address) {
        return AddressDTO.ofNonNull(address);
    }

}
