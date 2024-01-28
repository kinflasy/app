package br.org.kinflasy.api.entities.core;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class User extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @NonNull Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private @NonNull String username;

    @Column(name = "email", nullable = true)
    @Email
    private @NonNull String email;

    @Column(name = "email_verified_at")
    private @Nullable LocalDateTime emailVerifiedAt;

    @Column(name = "password", nullable = false)
    private @NonNull String password;

}
