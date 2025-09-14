package br.org.kinflasy.apis.people_filters.entities;

import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "is_people_filters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IdentityFilter extends PeopleFilter {

    @OneToOne(optional = false)
    private PersonDto person;

    @Override
    public Predicate<PersonDto> getPredicate() {
        return (p -> p.equals(this.person));
    }

    @Override
    public @NonNull String toString() {
        return "is " + person.getFullName() + " (#" + person.getId() + ")";
    }

}
