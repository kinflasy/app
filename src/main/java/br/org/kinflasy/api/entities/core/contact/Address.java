package br.org.kinflasy.api.entities.core.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public abstract class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "zip" , nullable = true)
    private String zip;

    @Column(name = "country" , nullable = true)
    private String country;

    @Column(name = "state" , nullable = true)
    private String state;

    @Column(name = "city" , nullable = true)
    private String city;

    @Column(name = "neighborhood" , nullable = true)
    private String neighborhood;

    @Column(name = "street" , nullable = true)
    private String street;

    @Column(name = "number" , nullable = true)
    private String number;

    @Column(name = "reference", nullable = true)
    private String reference;
    
}
