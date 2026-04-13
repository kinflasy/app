package br.org.kinflasy.apis.contacts.entities;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Immutable;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "addresses")
@Immutable
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Address extends AbstractSimpleAuditable {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Dados primitivos
     */

    @Column
    private String zip;

    @Column
    private String country;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String neighborhood;

    @Column
    private String street;

    @Column
    private String number;

    @Column
    private String complement;

    @Column
    private String reference;

}
