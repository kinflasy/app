package br.org.kinflasy.clients.local;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.contacts.controllers.AddressController;
import br.org.kinflasy.clients.AddressClient;
import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AddressLocalClient implements AddressClient {

    private final AddressController controller;

    @Override
    public List<AddressDto> listAll() {
        return controller.listAll().getBody();
    }

    @Override
    public AddressDto create(AddressRequest request) {
        return controller.create(request).getBody();
    }

    @Override
    public AddressDto findById(UUID id) {
        return controller.findById(id).getBody();
    }

    @Override
    public AddressDto update(UUID id, AddressRequest request) {
        return controller.update(id, request).getBody();
    }

    @Override
    public void delete(UUID id) {
        controller.delete(id);
    }

}
