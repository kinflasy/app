package br.org.kinflasy.api.entities.core.people_filter;

import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class PeopleFilter extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    public abstract Predicate<Person> getPredicate();

}
