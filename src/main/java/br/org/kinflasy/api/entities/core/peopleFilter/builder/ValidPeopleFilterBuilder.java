package br.org.kinflasy.api.entities.core.peoplefilter.builder;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.peoplefilter.PeopleFilter;

public abstract class ValidPeopleFilterBuilder {

    /**
     * Main filter
     */
    protected @NonNull PeopleFilter filter;

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    ValidPeopleFilterBuilder(final @NonNull PeopleFilter filter) {
        this.filter = filter;
    }

    /**
     * Get main filter
     * 
     * @return PeopleFilter
     */
    public @NonNull PeopleFilter build() {
        return filter;
    }

    @Override
    public @NonNull String toString() {
        return filter.toString();
    }

}
