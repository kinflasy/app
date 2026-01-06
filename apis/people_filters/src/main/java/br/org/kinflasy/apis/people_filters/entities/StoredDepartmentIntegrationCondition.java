package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conditions_department", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "department_id", "type" })
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class StoredDepartmentIntegrationCondition extends StoredCondition {

    @Column(nullable = false)
    private UUID departmentId;

    @Column
    private IntegrationType type = IntegrationType.INTEGRANT;

}
