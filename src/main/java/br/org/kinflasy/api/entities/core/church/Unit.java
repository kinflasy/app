package br.org.kinflasy.api.entities.core.church;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.User;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Unit extends AbstractAuditable<User, Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NonNull
    private Integer id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "slug", nullable = false)
    @NonNull
    private String slug;

    @Column(name = "email", nullable = false)
    @NonNull
    private String email;

    @Column(name = "phone", nullable = false)
    @NonNull
    private String phone;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    @NonNull
    private UnitType type;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "church_id")
    @NonNull
    private Church church;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @NonNull
    private Address address;

}
