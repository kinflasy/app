package br.org.kinflasy.api.entities.core.peopleFilter;

import java.util.function.Function;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "static_people_filters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class StaticPeopleFilter extends PeopleFilter {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "mode", unique = true)
    private PersonCharacteristic mode;

    @Override
    public Function<Person, Boolean> getFilter() {
        return mode.getFilter();
    }

}
