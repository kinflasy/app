package br.org.kinflasy.apis.churches.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.kinflasy.libs.contacts.dto.LinkDto;
import br.org.kinflasy.libs.contacts.dto.LinkRequest;
import jakarta.validation.Valid;

@FeignClient(name = "churches-linksApi", url = "${CONTACTS_API_URL}", path = "links")
public interface LinkClient {

    @GetMapping
    List<LinkDto> listAll();

    @PostMapping
    LinkDto create(@RequestBody @Valid LinkRequest request);

    @GetMapping("{id}")
    LinkDto findById(@PathVariable UUID id);

    @PutMapping("{id}")
    LinkDto update(@PathVariable UUID id, @RequestBody LinkRequest request);

    @DeleteMapping("{id}")
    void delete(@PathVariable UUID id);

}
