package br.org.kinflasy.api.entities.core.church;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.contracts.contact.Emailable;
import br.org.kinflasy.api.entities.core.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "churches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Church extends AbstractAuditable<User, Integer> implements Emailable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @NonNull Integer id;

    @Column(name = "name", nullable = false)
    private @NonNull String name;

    @Column(name = "slug", nullable = false, unique = true)
    private @NonNull String slug;

    @Column(name = "acronym")
    private @Nullable String acronym;

    @Column(name = "phone")
    private @Nullable String phone;

    @Column(name = "email", nullable = false, unique = true)
    private @NonNull String email;

    @Column(name = "email_verified_at")
    private @Nullable LocalDateTime emailVerifiedAt;

    @OneToMany(mappedBy = "church", cascade = CascadeType.ALL)
    private @NonNull List<Unit> units;

}
