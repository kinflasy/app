package br.org.kinflasy.api.apis.churches.entities.department;

import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.apis.churches.entities.Unit;
import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.apis.people_filters.entities.PeopleFilter;
import br.org.kinflasy.api.libs.churches.enums.department.DepartmentType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "departments")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Department extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private DepartmentType type;

    @ManyToOne(optional = false)
    private Unit unit;

    @ManyToOne(cascade = { CascadeType.PERSIST }, optional = false)
    private PeopleFilter visibilityFilter;

}
