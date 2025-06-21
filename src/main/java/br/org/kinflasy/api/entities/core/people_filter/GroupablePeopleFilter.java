package br.org.kinflasy.api.entities.core.people_filter;

import java.util.List;

import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class GroupablePeopleFilter extends PeopleFilter {

    @ManyToMany
    private List<PeopleFilter> filters;

}
