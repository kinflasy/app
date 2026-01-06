package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.InactivePersonClient;
import br.org.kinflasy.apis.churches.clients.UserClient;
import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivationUseCaseService {

    private final InactivePersonClient inactivePersonClient;
    private final UserClient userClient;

    private final MembershipRepository membershipRepository;

    private final ModelMapper mapper;

    public List<MembershipDto> activate(final UUID inactivePersonId, final UUID userId) {
        // Garantir existência do usuário ativo
        userClient.findById(userId);

        // Garantir existência da pessoa inativa
        inactivePersonClient.findById(inactivePersonId);

        // Caso não encontre alguma das pessoas, lança exceção FeignException.NotFound

        // Buscar membresias da pessoa inativa
        final var memberships = membershipRepository.findByPersonId(inactivePersonId).stream()

                // Substituir pessoa pelo usuário ativo
                .map(membership -> {
                    membership.setPersonId(userId);
                    membershipRepository.save(membership);

                    return mapper.map(membership, MembershipDto.class);
                })
                .toList();

        // Excluir pessoa inativa
        inactivePersonClient.delete(inactivePersonId);

        return memberships;
    }

}
