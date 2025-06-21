package br.org.kinflasy.api.entities.core.contact;

import org.springframework.data.annotation.Immutable;
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
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Address extends AbstractAuditable<User, Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "zip") 
    private String zip;

    @Column(name = "country") 
    private String country;

    @Column(name = "state") 
    private String state;

    @Column(name = "city") 
    private String city;

    @Column(name = "neighborhood") 
    private String neighborhood;

    @Column(name = "street") 
    private String street;

    @Column(name = "number") 
    private String number;

    @Column(name = "reference") 
    private String reference;
    
}
