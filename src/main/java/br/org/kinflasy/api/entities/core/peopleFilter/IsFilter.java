package br.org.kinflasy.api.entities.core.peopleFilter;

import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "is_people_filters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class IsFilter extends PeopleFilter {

    @ManyToOne
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    private @NonNull Person person;

    @Override
    public @NonNull Function<Person, Boolean> getFilter() {
        return (person -> person.equals(this.person));
    }
    
}
