package br.org.kinflasy.api.entities.core.people_filter;

import java.util.List;

public abstract class GroupablePeopleFilter extends PeopleFilter {

    public abstract List<PeopleFilter> getFilters();

    public abstract void setFilters(final List<PeopleFilter> filters);

}
