package br.org.kinflasy.api.entities.core.peopleFilter;

import java.util.function.Function;

import br.org.kinflasy.api.entities.core.User;
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
    @UniqueConstraint(columnNames = {"department_id", "type"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class DepartmentIntegrationFilter extends PeopleFilter {

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "type")
    private IntegrationType type = IntegrationType.INTEGRANT;

    @Override
    public Function<User, Boolean> getFilter() {
        // TODO: escrever regra de negócio (usar repository/service)
        throw new UnsupportedOperationException("Unimplemented method 'getFilter'");
    }
}
