package br.org.kinflasy.apis.churches.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.kinflasy.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import jakarta.validation.Valid;

@FeignClient(name = "inactivePeopleApi", contextId = "churches-inactivePeopleApi")
public interface InactivePersonClient {

    @PostMapping("admin")
    InactivePersonDto create(@RequestBody @Valid InactivePersonRequest request);

    @PostMapping("from-user")
    InactivePersonDto create(@RequestBody @Valid InactivePersonRequest.FromUser request);

    @GetMapping("{id}")
    InactivePersonDto findById(@PathVariable UUID id);

    @DeleteMapping("{id}")
    HttpStatus delete(@PathVariable UUID id);

}
