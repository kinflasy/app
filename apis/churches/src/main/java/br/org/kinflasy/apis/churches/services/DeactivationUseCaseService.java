package br.org.kinflasy.apis.churches.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.InactivePersonClient;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeactivationUseCaseService {

    private final InactivePersonClient inactivePersonClient;

    private final ChurchService churchService;
    private final UnitService unitService;
    private final MembershipService membershipService;

    @Transactional
    @PreAuthorize("@fga.check('church', #churchId, 'unit_admin', 'user', principal.id) or #userId.equals(principal.id)")
    public List<MembershipDto.Simple> deactivateOne(final UUID churchId, final UUID userId) {
        return deactivate(churchId, userId);
    }

    @Transactional
    @PreAuthorize("#userId.equals(principal.id)")
    public List<MembershipDto.Simple> deactivateAll(final UUID userId) {
        // Buscar relações de membresia do usuário em questão
        return membershipService.listByPersonId(userId).stream()

                // Obter IDs das igrejas
                .map(membership -> unitService.findById(membership.getUnit().getId()).map(UnitDto::getChurchId).orElse(null))
                .filter(Objects::nonNull)
                .distinct()

                // Desativar usuário em cada uma
                .map(churchId -> deactivate(churchId, userId))
                .flatMap(Collection::stream)
                .toList();
    }

    private List<MembershipDto.Simple> deactivate(final UUID churchId, final UUID userId) {
        // Criar pessoa inativa
        final var inactivePerson = inactivePersonClient
                .create(new InactivePersonRequest.FromUser().setChurchId(churchId).setUserId(userId));

        // Substituir usuário por pessoa inativa em todas as unidades
        return churchService.listUnits(churchId).stream()
                .map(unit -> unitService.findActiveMembership(unit.getId(), userId)
                        .map(membership -> membershipService.changePerson(membership.getId(), inactivePerson.getId()))
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

}
