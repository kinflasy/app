package br.org.kinflasy.api.entities.core.peoplefilter;

import java.util.List;

import org.springframework.lang.NonNull;

public abstract class GroupablePeopleFilter extends PeopleFilter {

    public abstract @NonNull List<PeopleFilter> getFilters();

    public abstract void setFilters(final @NonNull List<PeopleFilter> filters);

}
