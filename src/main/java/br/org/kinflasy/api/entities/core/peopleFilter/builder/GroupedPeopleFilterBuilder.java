package br.org.kinflasy.api.entities.core.peoplefilter.builder;

import java.util.List;
import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.peoplefilter.AndGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.peoplefilter.OrGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.peoplefilter.PeopleFilter;

public class GroupedPeopleFilterBuilder extends ValidPeopleFilterBuilder {

    /**
     * Package restricted constructor
     * 
     * @param filter
     */
    GroupedPeopleFilterBuilder(final @NonNull PeopleFilter filter) {
        super(filter);
    }

    public @NonNull GroupedPeopleFilterBuilder allThisAndMatchesAll(
            final @NonNull Function<FilterListBuilder, FilterListBuilder> person) {

        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndGroupPeopleFilter(List.of(filter, and));

        // Auto-retornar
        return this;

    }

    public @NonNull GroupedPeopleFilterBuilder allThisAndMatchesOneOf(
            final @NonNull Function<FilterListBuilder, FilterListBuilder> person) {
        
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual && adicionais
        filter = new AndGroupPeopleFilter(List.of(filter, or));

        // Auto-retornar
        return this;
        
    }

    public @NonNull GroupedPeopleFilterBuilder allThisOrMatchesAll(
            final @NonNull Function<FilterListBuilder, FilterListBuilder> person) {
        
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrGroupPeopleFilter(List.of(filter, and));

        // Auto-retornar
        return this;
        
    }

    public @NonNull GroupedPeopleFilterBuilder allThisOrMatchesOneOf(
            final @NonNull Function<FilterListBuilder, FilterListBuilder> person) {
        
        // Criar builder com filtros adicionais
        final var listed = person.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter(listed.getFiltersList());

        // Substituir filtro principal pelo atual || adicionais
        filter = new OrGroupPeopleFilter(List.of(filter, or));

        // Auto-retornar
        return this;
        
    }

}
