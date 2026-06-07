package br.org.kinflasy.apis.churches.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.people.dto.BirthdaySearchRequest;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.PersonIdentifierDto;
import br.org.kinflasy.libs.people.dto.PersonPhoneDto;
import br.org.kinflasy.libs.people.dto.UserIdentifierDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "churches-peopleApi", url = "${PEOPLE_API_URL}", path = "people")
public interface PersonClient {

    @GetMapping("identify/{id}")
    UserIdentifierDto identifyById(@PathVariable UUID id);

    @GetMapping("{id}")
    PersonDto findById(@PathVariable final UUID id);

    @GetMapping("search-birthdays")
    List<PersonIdentifierDto.WithBirthday> searchBirthdaysIn(@RequestBody final BirthdaySearchRequest request);

    @DeleteMapping("{id}")
    HttpStatus delete(@PathVariable final UUID id);

    @GetMapping("{id}/phone")
    PersonPhoneDto getPhone(@PathVariable final UUID id);

}
