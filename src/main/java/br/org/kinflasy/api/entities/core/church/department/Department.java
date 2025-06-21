package br.org.kinflasy.api.entities.core.church.department;

import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.people_filter.PeopleFilter;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;
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

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class Department extends AbstractAuditable<User, UUID>  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Unit unit;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private DepartmentType type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PeopleFilter visibilityFilter;

}
