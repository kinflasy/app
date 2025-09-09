package br.org.kinflasy.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;

@FeignClient(name = "address-api")
public interface AddressClient {

    @GetMapping
    List<AddressDto> listAll();

    @PostMapping
    AddressDto create(AddressRequest request);

    @GetMapping("/{id}")
    AddressDto findById(@PathVariable UUID id);

    @PutMapping("/{id}")
    AddressDto update(@PathVariable UUID id, AddressRequest request);

    @DeleteMapping("/{id}")
    AddressDto delete(@PathVariable UUID id);

}
