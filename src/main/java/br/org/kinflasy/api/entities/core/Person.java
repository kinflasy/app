package br.org.kinflasy.api.entities.core;

import java.time.LocalDate;

import br.org.kinflasy.api.entities.core.contact.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = true)
    private String nickname;

    @Column(name = "localDate", nullable = false)
    private LocalDate birthDate;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "phone", nullable = true)
    private String phone;
}