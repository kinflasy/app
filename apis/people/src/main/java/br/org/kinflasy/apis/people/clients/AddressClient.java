package br.org.kinflasy.apis.people.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import jakarta.validation.Valid;

@FeignClient(name = "people-addressesApi", url = "${CONTACTS_API_URL}", path = "addresses")
public interface AddressClient {

    @GetMapping
    List<AddressDto> listAll();

    @PostMapping
    AddressDto create(@RequestBody @Valid AddressRequest request);

    @PostMapping("register")
    AddressDto create(@RequestBody @Valid AddressRequest request, @RequestParam UUID createdBy);

    @GetMapping("{id}")
    AddressDto findById(@PathVariable UUID id);

    @PutMapping("{id}")
    AddressDto update(@PathVariable UUID id, @RequestBody AddressRequest request);

    @DeleteMapping("{id}")
    void delete(@PathVariable UUID id);

}
