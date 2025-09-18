package br.org.kinflasy.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;

@Component
public interface AddressClient {

    List<AddressDto> listAll();

    AddressDto create(AddressRequest request);

    AddressDto create(AddressRequest request, UUID createdBy);

    AddressDto findById(UUID id);

    AddressDto update(UUID id, AddressRequest request);

    void delete(UUID id);

}
