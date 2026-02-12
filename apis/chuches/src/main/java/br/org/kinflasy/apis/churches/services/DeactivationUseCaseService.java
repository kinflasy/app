package br.org.kinflasy.apis.churches.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.AddressClient;
import br.org.kinflasy.apis.churches.clients.InactivePersonClient;
import br.org.kinflasy.apis.churches.clients.UserClient;
import br.org.kinflasy.apis.churches.services.department.MembershipService;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import br.org.kinflasy.libs.people.dto.UserDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeactivationUseCaseService {

    private final InactivePersonClient inactivePersonClient;
    private final UserClient userClient;
    private final AddressClient addressClient;

    private final ChurchService churchService;
    private final UnitService unitService;
    private final MembershipService membershipService;

    private final ModelMapper mapper;

    @PreAuthorize("@fga.check('church', #churchId, 'admin', 'user', principal.id) or #userId.equals(principal.id)")
    public List<MembershipDto> deactivate(final UUID churchId, final UUID userId) {
        // Obter dados do usuário ativo
        final var user = userClient.findById(userId);
        final var address = addressClient.findById(user.getAddressId());

        return deactivate(churchId, user, address);
    }

    @PreAuthorize("#userId.equals(principal.id)")
    public List<MembershipDto> deactivateAll(final UUID userId) {
        // Obter dados do usuário ativo
        final var user = userClient.findById(userId);
        final var address = addressClient.findById(user.getAddressId());

        // Buscar relações de membresia do usuário em questão
        return membershipService.findByPersonId(userId).stream()

                // Obter IDs das igrejas
                .map(membership -> unitService.findById(membership.getUnitId()).map(UnitDto::getChurchId).orElse(null))
                .filter(Objects::nonNull)
                .distinct()

                // Desativar usuário em cada uma
                .map(churchId -> deactivate(churchId, user, address))
                .flatMap(Collection::stream)
                .toList();
    }

    private List<MembershipDto> deactivate(final UUID churchId, final UserDto user, final AddressDto address) {
        // Gerar requisições de nova pessoa inativa
        final var addressRequest = mapper.map(address, AddressRequest.class);
        final var inactivePersonRequest = mapper.map(user, InactivePersonRequest.class);
        inactivePersonRequest.setChurchId(churchId)
                .setAddress(addressRequest);

        // Criar pessoa inativa
        final var inactivePerson = inactivePersonClient.create(inactivePersonRequest);

        // Substituir usuário por pessoa inativa em todas as unidades
        return churchService.listUnits(churchId).stream()
                .map(unit -> unitService.findActiveMembership(unit.getId(), user.getId())
                        .map(membership -> membershipService.changePerson(membership.getId(),
                                inactivePerson.getId()))
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

}
