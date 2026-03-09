package br.org.kinflasy.apis.auth.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.Immutable;

import br.org.kinflasy.libs.people.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity(name = "authPerson")
@Table(name = "people")
@Immutable
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Accessors(chain = false)
public abstract class Person {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Enumerações
     */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /*
     * Dados primitivos
     */

    @Column(nullable = false)
    private String fullName;

    @Column
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthDate;

}
