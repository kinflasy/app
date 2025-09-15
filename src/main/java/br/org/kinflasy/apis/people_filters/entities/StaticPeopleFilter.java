package br.org.kinflasy.apis.people_filters.entities;

import org.springframework.lang.NonNull;

import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "static_people_filters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StaticPeopleFilter extends PeopleFilter {

    @Enumerated(EnumType.ORDINAL)
    @Column(unique = true)
    private PersonCharacteristic characteristic;

    @Override
    public boolean test(final PersonDto person) {
        return characteristic.getPredicate().test(person);
    }

    @Override
    public @NonNull String toString() {
        return "is " + characteristic.toString();
    }

}
