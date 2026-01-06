package br.org.kinflasy.apis.people_filters.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.kinflasy.libs.churches.dto.ChurchDto;
import br.org.kinflasy.libs.churches.dto.ChurchRequest;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import br.org.kinflasy.libs.people.dto.ActivationRequest;
import jakarta.validation.Valid;

@FeignClient(value = "churches-api", contextId = "people-filters.churches-api")
public interface ChurchClient {

    @GetMapping
    List<ChurchDto> listAll();

    @PostMapping
    ChurchDto create(@RequestBody @Valid final ChurchRequest request);

    @PostMapping("/starter")
    ChurchDto.Starter createStarter(@RequestBody @Valid final ChurchRequest.Starter request);

    @GetMapping("{id}")
    ChurchDto findById(@PathVariable final UUID id);

    @PutMapping("{id}")
    ChurchDto update(@PathVariable final UUID id, @RequestBody final ChurchRequest request);

    @DeleteMapping("{id}")
    HttpStatus delete(@PathVariable final UUID id);

    @GetMapping("{id}/units")
    List<UnitDto> listUnits(@PathVariable final UUID id);

    @PostMapping("{id}/units")
    UnitDto createUnit(@PathVariable final UUID id, @RequestBody @Valid final UnitRequest request);

    @PostMapping("activate-member")
    List<MembershipDto> activateMember(@RequestBody final ActivationRequest request);

}
