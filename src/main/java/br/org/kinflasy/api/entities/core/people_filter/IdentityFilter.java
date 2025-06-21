package br.org.kinflasy.api.entities.core.people_filter;

import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
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
    private Person person;

    @Override
    public Function<Person, Boolean> getFilter() {
        return (p -> p.equals(this.person));
    }

    @Override
    public @NonNull String toString() {
        return "is " + person.getFullName() + " (#" + person.getId() + ")";
    }

}
