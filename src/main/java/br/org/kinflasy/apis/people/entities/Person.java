package br.org.kinflasy.apis.people.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.contacts.contracts.Emailable;
import br.org.kinflasy.libs.people.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "people")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public abstract class Person extends AbstractSimpleAuditable<UUID> implements Emailable {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Chaves "estrangeiras" (referência)
     */

    @Column
    private UUID addressId;

    /*
     * Enumerações
     */

    @Enumerated
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

    @Column
    private String phone;

}
