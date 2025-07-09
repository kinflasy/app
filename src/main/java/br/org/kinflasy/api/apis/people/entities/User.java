package br.org.kinflasy.api.apis.people.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "users")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = true)
public class User extends Person {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column
    private LocalDateTime emailVerifiedAt;

    @Column(nullable = false)
    private String password;

}
