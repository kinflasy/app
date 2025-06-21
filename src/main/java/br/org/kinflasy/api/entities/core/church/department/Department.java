package br.org.kinflasy.api.entities.core.church.department;

import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.people_filter.PeopleFilter;
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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Department extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @ManyToOne(optional = false)
    private Unit unit;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private DepartmentType type;

    @ManyToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(nullable = false)
    private PeopleFilter visibilityFilter;

}
