package br.org.kinflasy.libs.people_filters.utils.builder;

import java.util.Objects;

import br.org.kinflasy.apis.people_filters.entities.ConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.Condition;

public abstract class ValidPeopleFilterBuilder {

    /**
     * Main filter
     */
    protected Condition filter;

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    ValidPeopleFilterBuilder(final Condition filter) {
        this.filter = filter;
    }

    private void simplify() {
        if (filter instanceof ConditionGroup groupablePeopleFilter) {
            filter = simplifyGroup(groupablePeopleFilter);
        }
    }

    private Condition simplifyGroup(final ConditionGroup group) {
        final var simplifiedFilters = group.getFilters().stream()
                .map(node -> {
                    if (node instanceof ConditionGroup innerGroup) {
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
    public Condition build() {
        simplify();
        return filter;
    }

    @Override
    public String toString() {
        return filter.toString();
    }

}
