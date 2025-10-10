package br.org.kinflasy.apis.people_filters.services;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people_filters.converters.StoredConditionConverter;
import br.org.kinflasy.apis.people_filters.factories.ConditionFactory;
import br.org.kinflasy.apis.people_filters.repositories.AndConditionGroupRepository;
import br.org.kinflasy.apis.people_filters.repositories.CharacteristicConditionRepository;
import br.org.kinflasy.apis.people_filters.repositories.ChurchMembershipConditionRepository;
import br.org.kinflasy.apis.people_filters.repositories.DepartmentIntegrationConditionRepository;
import br.org.kinflasy.apis.people_filters.repositories.ExtensionIntegrationInChurchConditionRepository;
import br.org.kinflasy.apis.people_filters.repositories.ExtensionIntegrationInUnitConditionRepository;
import br.org.kinflasy.apis.people_filters.repositories.IdentityConditionRepository;
import br.org.kinflasy.apis.people_filters.repositories.NegativeConditionRepository;
import br.org.kinflasy.apis.people_filters.repositories.OrConditionGroupRepository;
import br.org.kinflasy.apis.people_filters.repositories.UnitMembershipConditionRepository;
import br.org.kinflasy.libs.people_filters.conditions.business.CharacteristicCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ChurchMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.DepartmentIntegrationCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInChurchCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInUnitCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.IdentityCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.UnitMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.logical.AndConditionGroup;
import br.org.kinflasy.libs.people_filters.conditions.logical.NegativeCondition;
import br.org.kinflasy.libs.people_filters.conditions.logical.OrConditionGroup;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConditionService {

    private final ConditionFactory factory;
    private final StoredConditionConverter converter;

    private final NegativeConditionRepository negativeRepository;
    private final AndConditionGroupRepository andGroupRepository;
    private final OrConditionGroupRepository orGroupRepository;

    private final CharacteristicConditionRepository characteristicRepository;
    private final IdentityConditionRepository identityRepository;
    private final ChurchMembershipConditionRepository churchMembershipRepository;
    private final UnitMembershipConditionRepository unitMembershipRepository;
    private final DepartmentIntegrationConditionRepository departmentIntegrationRepository;

    private final ExtensionIntegrationInChurchConditionRepository extensionIntegrationInChurchRepository;
    private final ExtensionIntegrationInUnitConditionRepository extensionIntegrationInUnitRepository;

    public NegativeCondition findOrCreate(final NegativeCondition condition) {
        final var entity = negativeRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public AndConditionGroup findOrCreate(final AndConditionGroup condition) {
        final var entity = andGroupRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public OrConditionGroup findOrCreate(final OrConditionGroup condition) {
        final var entity = orGroupRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public CharacteristicCondition findOrCreate(final CharacteristicCondition condition) {
        final var entity = characteristicRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public IdentityCondition findOrCreate(final IdentityCondition condition) {
        final var entity = identityRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public ChurchMembershipCondition findOrCreate(final ChurchMembershipCondition condition) {
        final var entity = churchMembershipRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public UnitMembershipCondition findOrCreate(final UnitMembershipCondition condition) {
        final var entity = unitMembershipRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public DepartmentIntegrationCondition findOrCreate(final DepartmentIntegrationCondition condition) {
        final var entity = departmentIntegrationRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public ExtensionIntegrationInChurchCondition findOrCreate(final ExtensionIntegrationInChurchCondition condition) {
        final var entity = extensionIntegrationInChurchRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public ExtensionIntegrationInUnitCondition findOrCreate(final ExtensionIntegrationInUnitCondition condition) {
        final var entity = extensionIntegrationInUnitRepository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

    public <C extends Condition> C findOrCreate(final C condition) {
        final var repository = factory.getRepository(condition);
        final var entity = repository.findOrCreate(converter.toEntity(condition));
        return converter.toDto(entity);
    }

}
