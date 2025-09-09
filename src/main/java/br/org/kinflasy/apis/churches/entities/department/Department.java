package br.org.kinflasy.apis.churches.entities.department;

import java.util.UUID;

import br.org.kinflasy.apis.churches.entities.Unit;
import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.churches.enums.department.DepartmentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "departments", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "unit_id", "name" }),
        @UniqueConstraint(columnNames = { "unit_id", "slug" }) })
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Department extends AbstractSimpleAuditable<UUID> {

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

}
