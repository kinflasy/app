package br.org.kinflasy.api.entities.core.church;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.User;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "churches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class Church extends AbstractAuditable<User, Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NonNull
    private Integer id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    @NonNull
    private String slug;

    @Column(name = "phone")
    @Nullable
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    @NonNull
    private String email;

    @Column(name = "email_verified_at")
    @Nullable
    private LocalDateTime emailVerifiedAt;

}
