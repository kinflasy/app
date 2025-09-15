package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;
import java.util.function.Predicate;

import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(nullable = false)
    private UUID personId;

    @Override
    public Predicate<PersonDto> getPredicate() {
        return (p -> p.getId().equals(personId));
    }

}
