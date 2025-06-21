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
    @Column
    private Integer id;
    
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
