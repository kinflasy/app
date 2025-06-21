package br.org.kinflasy.api.entities.core.people_filter.builder;

import java.util.Objects;

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

    private void simplify() {
        if (filter instanceof GroupablePeopleFilter groupablePeopleFilter) {
            filter = simplifyGroup(groupablePeopleFilter);
        }
    }

    private PeopleFilter simplifyGroup(final GroupablePeopleFilter group) {
        final var simplifiedFilters = group.getFilters().stream()
                .map(node -> {
                    if (node instanceof GroupablePeopleFilter innerGroup) {
                        return simplifyGroup(innerGroup);
                    }
                    return node;
                })
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (simplifiedFilters.isEmpty()) {
            // Delete empty group
            return null;
        } else if (simplifiedFilters.size() == 1) {
            // Promote the only child
            return simplifiedFilters.getFirst();
        }

        // Replace the filters
        group.setFilters(simplifiedFilters);

        // Return self
        return group;
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
