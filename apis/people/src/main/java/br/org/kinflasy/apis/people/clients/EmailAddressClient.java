package br.org.kinflasy.apis.people.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.kinflasy.libs.contacts.dto.EmailAddressDto;
import br.org.kinflasy.libs.contacts.dto.EmailAddressRequest;
import jakarta.validation.Valid;

@FeignClient(name = "people-emailAddressesApi", url = "${CONTACTS_API_URL}", path = "email-addresses")
public interface EmailAddressClient {

    @PostMapping
    EmailAddressDto create(@Valid @RequestBody final EmailAddressRequest request);

    @GetMapping("{id}")
    EmailAddressDto findById(@PathVariable final UUID id);

    @DeleteMapping("{id}")
    Void delete(@PathVariable final UUID id);

}
