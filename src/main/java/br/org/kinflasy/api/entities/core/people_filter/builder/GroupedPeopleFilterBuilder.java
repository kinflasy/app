package br.org.kinflasy.api.entities.core.people_filter.builder;

import java.util.List;
import java.util.function.UnaryOperator;

import br.org.kinflasy.api.entities.core.people_filter.AndGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.OrGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.PeopleFilter;

public class GroupedPeopleFilterBuilder extends ValidPeopleFilterBuilder {

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    GroupedPeopleFilterBuilder(final PeopleFilter filter) {
        super(filter);
    }

    public GroupedPeopleFilterBuilder allThisAndMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndGroupPeopleFilter().setFilters(List.of(filter, and));

        // Auto-retornar
        return this;
    }

    public GroupedPeopleFilterBuilder allThisAndMatchesOneOf(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndGroupPeopleFilter().setFilters(List.of(filter, or));

        // Auto-retornar
        return this;
    }

    public GroupedPeopleFilterBuilder allThisOrMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrGroupPeopleFilter().setFilters(List.of(filter, and));

        // Auto-retornar
        return this;
    }

    public GroupedPeopleFilterBuilder allThisOrMatchesOneOf(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter().setFilters(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrGroupPeopleFilter().setFilters(List.of(filter, or));

        // Auto-retornar
        return this;
    }

}
