package br.org.kinflasy.apis.churches.entities;

import java.util.List;
import java.util.UUID;

import br.org.kinflasy.apis.churches.entities.department.Department;
import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.contacts.contracts.Emailable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "units", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "church_id", "name" }),
        @UniqueConstraint(columnNames = { "church_id", "slug" })
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Unit extends AbstractSimpleAuditable<UUID> implements Emailable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private UnitType type;

    @Column(nullable = false)
    private UUID addressId;

    @Column(nullable = false)
    private UUID churchId;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "unit")
    private List<Department> departments;

}
