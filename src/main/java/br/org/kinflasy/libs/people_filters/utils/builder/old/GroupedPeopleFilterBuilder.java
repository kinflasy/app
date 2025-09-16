package br.org.kinflasy.libs.people_filters.utils.builder.old;

import java.util.List;
import java.util.function.UnaryOperator;

import br.org.kinflasy.apis.people_filters.entities.AndConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.OrConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.Condition;

public class GroupedPeopleFilterBuilder extends ValidPeopleFilterBuilder {

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    GroupedPeopleFilterBuilder(final Condition filter) {
        super(filter);
    }

    public GroupedPeopleFilterBuilder allThisAndMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndConditionGroup().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndConditionGroup().setFilters(List.of(filter, and));

        // Auto-retornar
        return this;
    }

    public GroupedPeopleFilterBuilder allThisAndMatchesOneOf(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrConditionGroup().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndConditionGroup().setFilters(List.of(filter, or));

        // Auto-retornar
        return this;
    }

    public GroupedPeopleFilterBuilder allThisOrMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndConditionGroup().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrConditionGroup().setFilters(List.of(filter, and));

        // Auto-retornar
        return this;
    }

    public GroupedPeopleFilterBuilder allThisOrMatchesOneOf(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrConditionGroup().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrConditionGroup().setFilters(List.of(filter, or));

        // Auto-retornar
        return this;
    }

}
