package br.org.kinflasy.libs.people_filters.utils.builder.old;

import java.util.Objects;

import br.org.kinflasy.apis.people_filters.entities.StoredConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredCondition;

public abstract class ValidPeopleFilterBuilder {

    /**
     * Main filter
     */
    protected StoredCondition filter;

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    ValidPeopleFilterBuilder(final StoredCondition filter) {
        this.filter = filter;
    }

    private void simplify() {
        if (filter instanceof StoredConditionGroup groupablePeopleFilter) {
            filter = simplifyGroup(groupablePeopleFilter);
        }
    }

    private StoredCondition simplifyGroup(final StoredConditionGroup group) {
        final var simplifiedConditions = group.getConditions().stream()
                .map(node -> {
                    if (node instanceof StoredConditionGroup innerGroup) {
                        return simplifyGroup(innerGroup);
                    }
                    return node;
                })
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (simplifiedConditions.isEmpty()) {
            // Delete empty group
            return null;
        } else if (simplifiedConditions.size() == 1) {
            // Promote the only child
            return simplifiedConditions.getFirst();
        }

        // Replace the conditions
        group.setConditions(simplifiedConditions);

        // Return self
        return group;
    }

    /**
     * Get main filter
     * 
     * @return PeopleFilter
     */
    public StoredCondition build() {
        simplify();
        return filter;
    }

    @Override
    public String toString() {
        return filter.toString();
    }

}
