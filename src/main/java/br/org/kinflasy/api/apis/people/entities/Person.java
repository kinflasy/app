package br.org.kinflasy.api.apis.people.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.apis.churches.entities.department.Integration;
import br.org.kinflasy.api.apis.churches.entities.membership.Membership;
import br.org.kinflasy.api.apis.contacts.entities.Address;
import br.org.kinflasy.api.libs.contacts.contracts.Emailable;
import br.org.kinflasy.api.libs.people.enums.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "people")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public abstract class Person extends AbstractAuditable<User, UUID> implements Emailable {

    public static final Integer ADULT_AGE = 18;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column
    private String nickname;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column
    private String phone;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "person")
    private List<Membership> memberships;

    @OneToMany(mappedBy = "person")
    private List<Integration> integrations;

}
