package br.org.kinflasy.api.entities.core.people_filter.builder;

import java.util.List;
import java.util.function.Function;


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

    public GroupedPeopleFilterBuilder allThisAndMatchesAll(
            final Function<FilterListBuilder, FilterListBuilder> person) {

        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndGroupPeopleFilter(List.of(filter, and));

        // Auto-retornar
        return this;

    }

    public GroupedPeopleFilterBuilder allThisAndMatchesOneOf(
            final Function<FilterListBuilder, FilterListBuilder> person) {
        
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndGroupPeopleFilter(List.of(filter, or));

        // Auto-retornar
        return this;
        
    }

    public GroupedPeopleFilterBuilder allThisOrMatchesAll(
            final Function<FilterListBuilder, FilterListBuilder> person) {
        
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrGroupPeopleFilter(List.of(filter, and));

        // Auto-retornar
        return this;
        
    }

    public GroupedPeopleFilterBuilder allThisOrMatchesOneOf(
            final Function<FilterListBuilder, FilterListBuilder> person) {
        
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrGroupPeopleFilter(List.of(filter, or));

        // Auto-retornar
        return this;
        
    }

}
