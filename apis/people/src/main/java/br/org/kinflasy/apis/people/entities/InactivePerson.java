package br.org.kinflasy.apis.people.entities;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "inactive_people")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = true)
public class InactivePerson extends Person {

    /*
     * Chaves estrangeiras
     */
    @Column(nullable = false)
    private UUID churchId;

    /*
     * Dados primitivos
     */

    @Column
    private String email;

}
