package br.org.kinflasy.api.entities.core.church;

import java.util.List;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.contact.Address;
import br.org.kinflasy.api.utils.enums.core.church.UnitType;
import io.micrometer.common.lang.Nullable;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "units", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "church_id", "name" }),
        @UniqueConstraint(columnNames = { "church_id", "slug" })
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Unit extends AbstractAuditable<User, Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @NonNull Integer id;

    @Column(name = "name", nullable = false)
    private @NonNull String name;

    @Column(name = "slug", nullable = false)
    private @NonNull String slug;

    @Column(name = "email", nullable = false)
    private @NonNull String email;

    @Column(name = "phone", nullable = false)
    private @Nullable String phone;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private @NonNull UnitType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "church_id")
    private @NonNull Church church;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, optional = false, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private @NonNull Address address;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private @NonNull List<Department> departments;

}
