package br.org.kinflasy.api.entities.core.church;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.entities.core.church.membership.Membership;
import br.org.kinflasy.api.entities.core.contact.Address;
import br.org.kinflasy.api.utils.enums.core.church.UnitType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "units", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "church_id", "name" }),
        @UniqueConstraint(columnNames = { "church_id", "slug" })
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class Unit extends AbstractAuditable<User, UUID> {

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

    @Column
    @Enumerated(EnumType.ORDINAL)
    private UnitType type;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Church church;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Address address;

    @OneToMany(mappedBy = "unit")
    private Set<Membership> memberships;

}
