package br.org.kinflasy.libs.people_filters.utils.builder;

import java.util.List;
import java.util.function.UnaryOperator;

import br.org.kinflasy.apis.people_filters.entities.AndConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.OrConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.Condition;

public class SinglyPeopleFilterBuilder extends ValidPeopleFilterBuilder {

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    SinglyPeopleFilterBuilder(final Condition filter) {
        super(filter);
    }

    public GroupedPeopleFilterBuilder andMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndConditionGroup().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndConditionGroup().setFilters(List.of(filter, and));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);
    }

    public GroupedPeopleFilterBuilder andMatchesOneOf(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrConditionGroup().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndConditionGroup().setFilters(List.of(filter, or));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);
    }

    public GroupedPeopleFilterBuilder orMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndConditionGroup().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrConditionGroup().setFilters(List.of(filter, and));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);
    }

    public GroupedPeopleFilterBuilder orMatchesOneOf(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrConditionGroup().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrConditionGroup().setFilters(List.of(filter, or));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);
    }

}
