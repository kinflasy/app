package br.org.kinflasy.api.entities.core.church.department;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.peopleFilter.PeopleFilter;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Department extends AbstractAuditable<User, Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @NonNull Integer id;

    @Column(name = "name", nullable = false)
    private @NonNull String name;

    @Column(name = "slug", nullable = false)
    private @NonNull String slug;

    @ManyToOne(optional = false)
    private @NonNull Unit unit;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private @NonNull DepartmentType type;

    @ManyToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "visibility_filter", nullable = false)
    private @NonNull PeopleFilter visibilityFilter;

}
