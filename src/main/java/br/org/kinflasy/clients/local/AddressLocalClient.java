package br.org.kinflasy.clients.local;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.contacts.controllers.AddressController;
import br.org.kinflasy.clients.AddressClient;
import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class AddressLocalClient implements AddressClient {

    private final AddressController controller;

    @Override
    public List<AddressDto> listAll() {
        log.info("\n\nLOCAL\n\n");
        return controller.listAll().getBody();
    }

    @Override
    public AddressDto create(AddressRequest request) {
        log.info("\n\nLOCAL\n\n");
        return controller.create(request).getBody();
    }

    @Override
    public AddressDto findById(UUID id) {
        log.info("\n\nLOCAL\n\n");
        return controller.findById(id).getBody();
    }

    @Override
    public AddressDto update(UUID id, AddressRequest request) {
        log.info("\n\nLOCAL\n\n");
        return controller.update(id, request).getBody();
    }

    @Override
    public void delete(UUID id) {
        log.info("\n\nLOCAL\n\n");
        controller.delete(id);
    }

}
