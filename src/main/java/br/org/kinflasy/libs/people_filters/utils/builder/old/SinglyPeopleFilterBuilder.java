package br.org.kinflasy.libs.people_filters.utils.builder.old;

import java.util.List;
import java.util.function.UnaryOperator;

import br.org.kinflasy.apis.people_filters.entities.StoredAndConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredOrConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredCondition;

public class SinglyPeopleFilterBuilder extends ValidPeopleFilterBuilder {

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    SinglyPeopleFilterBuilder(final StoredCondition filter) {
        super(filter);
    }

    public GroupedPeopleFilterBuilder andMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new StoredAndConditionGroup().setConditions(listed.getConditionsList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new StoredAndConditionGroup().setConditions(List.of(filter, and));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);
    }

    public GroupedPeopleFilterBuilder andMatchesOneOf(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new StoredOrConditionGroup().setConditions(listed.getConditionsList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new StoredAndConditionGroup().setConditions(List.of(filter, or));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);
    }

    public GroupedPeopleFilterBuilder orMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new StoredAndConditionGroup().setConditions(listed.getConditionsList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new StoredOrConditionGroup().setConditions(List.of(filter, and));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);
    }

    public GroupedPeopleFilterBuilder orMatchesOneOf(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new StoredOrConditionGroup().setConditions(listed.getConditionsList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new StoredOrConditionGroup().setConditions(List.of(filter, or));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);
    }

}
