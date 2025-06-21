package br.org.kinflasy.api.entities.core.people_filter;

import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.utils.enums.core.church.department.IntegrationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(optional = false)
    private Department department;

    @Column
    private IntegrationType type = IntegrationType.INTEGRANT;

    @Override
    public Function<Person, Boolean> getFilter() {
        return (person -> person.getIntegrations().stream()
                .anyMatch(integration -> integration.getDepartment().equals(department)
                        && integration.getType() == type));
    }
    
    @Override
    public @NonNull String toString() {
        return "is " + type.toString() + " of the department " + department.getName() + " (#" + department.getId() + ")";
    }

}
