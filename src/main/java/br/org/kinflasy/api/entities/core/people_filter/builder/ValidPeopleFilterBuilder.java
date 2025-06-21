package br.org.kinflasy.api.entities.core.people_filter.builder;


import br.org.kinflasy.api.entities.core.people_filter.GroupablePeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.PeopleFilter;

public abstract class ValidPeopleFilterBuilder {

    /**
     * Main filter
     */
    protected PeopleFilter filter;

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    ValidPeopleFilterBuilder(final PeopleFilter filter) {
        this.filter = filter;
    }

    private PeopleFilter simplifyGroup(final GroupablePeopleFilter group) {
        final var simplifiedFilters = group.getFilters().stream()
                .map(node -> {
                    if (node instanceof GroupablePeopleFilter) {
                        return simplifyGroup((GroupablePeopleFilter) node);
                    }
                    return node;
                })
                .filter(node -> node != null)
                .distinct()
                .toList();

        if (simplifiedFilters.size() == 0) {

            // Delete empty group
            return null;

        } else if (simplifiedFilters.size() == 1) {

            // Promote the only child
            return simplifiedFilters.get(0);
            
        }

        // Replace the filters
        group.setFilters(simplifiedFilters);

        // Return self
        return group;
    }

    private void simplify() {
        if (filter instanceof GroupablePeopleFilter) {
            filter = simplifyGroup((GroupablePeopleFilter) filter);
        }
    }

    /**
     * Get main filter
     * 
     * @return PeopleFilter
     */
    public PeopleFilter build() {
        simplify();
        return filter;
    }

    @Override
    public String toString() {
        return filter.toString();
    }

}
