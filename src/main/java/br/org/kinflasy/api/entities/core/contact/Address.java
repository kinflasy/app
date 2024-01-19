package br.org.kinflasy.api.entities.core.contact;

import org.springframework.data.annotation.Immutable;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
    @NonNull
    private Integer id;
    
    @Column(name = "zip")
    @Nullable
    private String zip;

    @Column(name = "country")
    @Nullable
    private String country;

    @Column(name = "state")
    @Nullable
    private String state;

    @Column(name = "city")
    @Nullable
    private String city;

    @Column(name = "neighborhood")
    @Nullable
    private String neighborhood;

    @Column(name = "street")
    @Nullable
    private String street;

    @Column(name = "number")
    @Nullable
    private String number;

    @Column(name = "reference")
    @Nullable
    private String reference;
    
}
