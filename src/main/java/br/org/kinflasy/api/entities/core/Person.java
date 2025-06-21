package br.org.kinflasy.api.entities.core;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.contracts.contact.Emailable;
import br.org.kinflasy.api.entities.core.church.department.Integration;
import br.org.kinflasy.api.entities.core.church.membership.Membership;
import br.org.kinflasy.api.entities.core.contact.Address;
import br.org.kinflasy.api.utils.enums.core.Gender;
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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "people")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public abstract class Person extends AbstractAuditable<User, Integer> implements Emailable {

    public static final Integer ADULT_AGE = 18;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "nickname")
    private @Nullable String nickname;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "phone")
    private @Nullable String phone;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
    private @Nullable Address address;

    @OneToMany(mappedBy = "person")
    private List<Membership> memberships;

    @OneToMany(mappedBy = "person")
    private List<Integration> integrations;

}