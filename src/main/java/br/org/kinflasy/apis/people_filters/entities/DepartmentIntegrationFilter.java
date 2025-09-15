package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department_people_filters", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "department_id", "type" })
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentIntegrationFilter extends PeopleFilter {

    @Column(nullable = false)
    private UUID departmentId;

    @Column
    private IntegrationType type = IntegrationType.INTEGRANT;

    @Override
    public boolean test(final PersonDto person) {
        // TODO implementar filtro
        return false;
    }

}
