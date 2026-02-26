package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.InactivePersonClient;
import br.org.kinflasy.apis.churches.clients.UserClient;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivationUseCaseService {

    private final InactivePersonClient inactivePersonClient;
    private final UserClient userClient;

    private final MembershipService membershipService;

    @PreAuthorize("@fga.check('person_data', #inactivePersonId, 'can_edit', 'user', principal.id)")
    public List<MembershipDto> activate(final UUID inactivePersonId, final UUID userId) {
        // Garantir existência do usuário ativo
        // Caso não encontre, lança exceção FeignException.NotFound
        userClient.identifyById(userId);

        // Buscar membresias da pessoa inativa
        final var memberships = membershipService.findByPersonId(inactivePersonId).stream()

                // Substituir pessoa pelo usuário ativo
                .map(membership -> membershipService.changePerson(membership.getId(), userId))
                .toList();

        // Excluir pessoa inativa
        inactivePersonClient.delete(inactivePersonId);

        return memberships;
    }

}
