package br.org.kinflasy.api.entities.core;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
public class Person extends AbstractAuditable<User, Integer> {

    public final static Integer ADULT_AGE = 18;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @NonNull Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private @NonNull String name;

    @Column(name = "nickname")
    private @Nullable String nickname;

    @Column(name = "gender", nullable = false)
    private @NonNull Gender gender;

    @Column(name = "birth_date", nullable = false)
    private @NonNull LocalDate birthDate;

    @Column(name = "phone")
    private @Nullable String phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private @Nullable Address address;

    @OneToMany(mappedBy = "person")
    private @NonNull List<Membership> memberships;

    @OneToMany(mappedBy = "person")
    private @NonNull List<Integration> integrations;

}