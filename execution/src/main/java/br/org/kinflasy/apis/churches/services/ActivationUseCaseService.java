package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.apis.people.services.InactivePersonService;
import br.org.kinflasy.apis.people.services.UserService;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivationUseCaseService {

    private final InactivePersonService inactivePersonService;
    private final UserService userService;

    private final MembershipRepository membershipRepository;

    private final ModelMapper mapper;

    public List<MembershipDto> activate(final UUID inactivePersonId, final UUID userId) {
        // Obter dados do usuário ativo
        return userService.findById(userId)

                // Obter dados da pessoa inativa
                .flatMap(user -> inactivePersonService.findById(inactivePersonId))

                .map(inactivePerson -> {
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
                    inactivePersonService.delete(inactivePersonId);

                    return memberships;
                })

                // Caso não encontre alguma das pessoas, lança exceção
                .orElseThrow();
    }

}
