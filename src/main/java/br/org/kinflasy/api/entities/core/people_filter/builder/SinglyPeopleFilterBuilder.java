package br.org.kinflasy.api.entities.core.people_filter.builder;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;


import br.org.kinflasy.api.entities.core.people_filter.AndGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.OrGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.PeopleFilter;

public class SinglyPeopleFilterBuilder extends ValidPeopleFilterBuilder {

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    SinglyPeopleFilterBuilder(final PeopleFilter filter) {
        super(filter);
    }

    public GroupedPeopleFilterBuilder andMatchesAll(final UnaryOperator<FilterListBuilder> person) {
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndGroupPeopleFilter(List.of(filter, and));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);

    }

    public GroupedPeopleFilterBuilder andMatchesOneOf(
            final Function<FilterListBuilder, FilterListBuilder> person) {

        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndGroupPeopleFilter(List.of(filter, or));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);

    }

    public GroupedPeopleFilterBuilder orMatchesAll(
            final Function<FilterListBuilder, FilterListBuilder> person) {

        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrGroupPeopleFilter(List.of(filter, and));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);

    }

    public GroupedPeopleFilterBuilder orMatchesOneOf(
            final Function<FilterListBuilder, FilterListBuilder> person) {

        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrGroupPeopleFilter(List.of(filter, or));

        // Transformar-se em Grouped
        return new GroupedPeopleFilterBuilder(filter);

    }

}
