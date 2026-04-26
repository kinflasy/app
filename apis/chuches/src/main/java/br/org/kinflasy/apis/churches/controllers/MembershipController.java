package br.org.kinflasy.apis.churches.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.churches.services.MembershipService;
import br.org.kinflasy.apis.churches.services.department.IntegrationService;
import br.org.kinflasy.libs.churches.dto.MembershipDto.Pending;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/church/unit/memberships")
@Tag(name = "Membership")
@AllArgsConstructor
public class MembershipController {

    private final IntegrationService integrationService;
    private final MembershipService service;

    @GetMapping("/pending")
    public ResponseEntity<List<Pending>> listPendingForLoggedUser() {
        return ResponseEntity.ok(service.listPendingForLoggedUser());
    }

    @GetMapping("{id}/integrations")
    public ResponseEntity<List<IntegrationDto>> listIntegrations(@PathVariable final UUID id) {
        return ResponseEntity.ok(integrationService.listByMembership(id));
    }

}
