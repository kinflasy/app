package br.org.kinflasy.api.entities.core.people_filter;

import java.util.UUID;
import java.util.function.Function;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
public abstract class PeopleFilter extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    protected UUID id;

    public abstract Function<Person, Boolean> getFilter();

}
