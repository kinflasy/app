package br.org.kinflasy.apis.people.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Table(name = "users")
@DynamicUpdate
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = true)
public class User extends Person {

    /*
     * Dados primitivos
     */

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column
    private LocalDateTime emailVerifiedAt;

    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    @Override
    @PrePersist
    protected void onPreCreate() {
        setCreatedBy(getId());
        setCreatedDate(LocalDateTime.now());
    }

    @Override
    @PreUpdate
    protected void onPreUpdate() {
        if (getLastModifiedBy() == null) {
            setLastModifiedBy(getId());
            setLastModifiedDate(LocalDateTime.now());
        } else {
            super.onPreUpdate();
        }
    }

}
