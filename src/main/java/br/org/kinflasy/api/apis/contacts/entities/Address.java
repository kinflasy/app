package br.org.kinflasy.api.apis.contacts.entities;

import java.util.UUID;

import org.springframework.data.annotation.Immutable;
import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.apis.people.entities.User;
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
@Table(name = "addresses")
@Immutable
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Address extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
    private String reference;

}
