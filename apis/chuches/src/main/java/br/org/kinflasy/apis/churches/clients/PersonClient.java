package br.org.kinflasy.apis.churches.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.people.dto.PersonDto;

@FeignClient(name = "churches-peopleApi", url = "${PEOPLE_API_URL}", path = "people")
public interface PersonClient {

    @GetMapping("{id}")
    ResponseEntity<PersonDto> findById(@PathVariable final UUID id);

    @DeleteMapping("{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable final UUID id);

}
