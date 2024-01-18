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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "people")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class Person extends AbstractAuditable<User, Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NonNull
    private Integer id;

    @Column(name = "name", nullable = false)
    @NonNull
    @NotBlank
    private String name;

    @Column(name = "nickname")
    @Nullable
    private String nickname;

    @Column(name = "gender", nullable = false)
    @NonNull
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    @NonNull
    private LocalDate birthDate;

    @Column(name = "phone")
    @Nullable
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @Nullable
    private Address address;

    @OneToMany(mappedBy = "person")
    private List<Membership> memberships;

    @OneToMany(mappedBy = "person")
    private List<Integration> integrations;

}