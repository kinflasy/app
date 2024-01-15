package br.org.kinflasy.api.entities.core.peopleFilter;

import java.util.function.Function;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.utils.enums.core.StaticPeopleFilterMode;
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
public abstract class StaticPeopleFilter extends PeopleFilter {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "mode", unique = true)
    private StaticPeopleFilterMode mode;

    @Override
    public Function<User, Boolean> getFilter() {
        return mode.getFilter();
    }

}
