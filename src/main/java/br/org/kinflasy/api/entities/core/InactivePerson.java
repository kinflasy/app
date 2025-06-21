package br.org.kinflasy.api.entities.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "inactive_people")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = true)
public class InactivePerson extends Person {

    @Email
    @Column
    private String email;

}
