package br.org.kinflasy.api.entities.core;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.AbstractAuditable;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractAuditable<User, Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "emailVerifiedAt")
    @Nullable
    private LocalDateTime emailVerifiedAt;

    @Column(name = "password")
    private String password;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Person person;
}
