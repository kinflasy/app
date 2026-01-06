package br.org.kinflasy.apis.people_filters.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredDepartmentIntegrationCondition;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;

@Repository
public interface DepartmentIntegrationConditionRepository
        extends ConditionRepository<StoredDepartmentIntegrationCondition>,
        JpaRepository<StoredDepartmentIntegrationCondition, UUID> {

    Optional<StoredDepartmentIntegrationCondition> findByDepartmentIdAndType(UUID departmentId,
            IntegrationType type);

    default StoredDepartmentIntegrationCondition findOrCreate(
            final StoredDepartmentIntegrationCondition condition) {
        return findByDepartmentIdAndType(condition.getDepartmentId(), condition.getType())
                .orElseGet(() -> save(condition));
    }

}
