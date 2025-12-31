package br.org.kinflasy.apis.people.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.apis.churches.repositories.department.IntegrationRepository;
import br.org.kinflasy.apis.people_filters.repositories.IdentityConditionRepository;
import br.org.kinflasy.libs.people.dto.UserSimpleDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivationUseCaseService {

    private final InactivePersonService inactivePersonService;
    private final UserService userService;

    private final MembershipRepository membershipRepository;
    private final IntegrationRepository integrationRepository;
    private final IdentityConditionRepository identityConditionRepository;

    private final ModelMapper mapper;

    public UserSimpleDto activate(final UUID inactivePersonId, final UUID userId) {
        return userService.findById(userId)
                .flatMap(user -> inactivePersonService.findById(inactivePersonId)
                        .map(inactivePerson -> {
                            membershipRepository.findByPersonId(inactivePersonId)
                                    .forEach(membership -> membership.setPersonId(userId));

                            integrationRepository.findByMembershipId(inactivePersonId)
                                    .forEach(integration -> integration.setMembershipId(userId));

                            identityConditionRepository.findByPersonId(inactivePersonId)
                                    .ifPresent(condition -> condition.setPersonId(userId));

                            return mapper.map(user, UserSimpleDto.class);
                        }))
                .orElseThrow();
    }

}
