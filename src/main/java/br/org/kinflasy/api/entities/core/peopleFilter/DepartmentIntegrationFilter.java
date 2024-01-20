package br.org.kinflasy.api.entities.core.peopleFilter;

import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.utils.enums.core.church.department.IntegrationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department_people_filters", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "department_id", "type" })
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class DepartmentIntegrationFilter extends PeopleFilter {

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private @NonNull Department department;

    @Column(name = "type")
    private @NonNull IntegrationType type = IntegrationType.INTEGRANT;

    @Override
    public @NonNull Function<Person, Boolean> getFilter() {
        return (person -> person.getIntegrations().stream()
                .anyMatch(integration -> integration.getDepartment().equals(department)
                        && integration.getType() == type));
    }
}
