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

import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.churches.dto.MembershipSimpleDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import jakarta.validation.Valid;

@FeignClient(value = "church-base-api", contextId = "people-filters.church-base-api.units", path = "units")
public interface UnitClient {

    @GetMapping("{id}")
    UnitDto findById(@PathVariable final UUID id);

    @PutMapping("{id}")
    UnitDto update(@PathVariable final UUID id, @RequestBody final UnitRequest request);

    @DeleteMapping("{id}")
    HttpStatus delete(@PathVariable final UUID id);

    @GetMapping("{id}/departments")
    List<DepartmentDto> listDepartments(@PathVariable final UUID id);

    @PostMapping("{id}/departments")
    DepartmentDto createDepartment(@PathVariable final UUID id,
            @RequestBody @Valid final DepartmentRequest request);

    @GetMapping("{id}/members")
    List<MembershipDto> listMembers(@PathVariable final UUID id);

    @GetMapping("{id}/members-and-ex-members")
    List<MembershipDto> listMembersAndExMembers(@PathVariable final UUID id);

    @GetMapping("{id}/membership/{personId}")
    MembershipSimpleDto findActiveMembership(@PathVariable final UUID id, @PathVariable final UUID personId);

    @PostMapping("{id}/members")
    List<MembershipSimpleDto> associateMembers(@PathVariable final UUID id,
            @RequestBody @Valid final List<MembershipRequest> request);

    @PostMapping("{id}/members/register")
    List<MembershipSimpleDto> registerMembers(@PathVariable final UUID id,
            @RequestBody final List<MembershipRequest.Register> request);

}
