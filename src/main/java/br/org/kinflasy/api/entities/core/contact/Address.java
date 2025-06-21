package br.org.kinflasy.api.entities.core.contact;

import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.entities.core.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public abstract class Address extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
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
