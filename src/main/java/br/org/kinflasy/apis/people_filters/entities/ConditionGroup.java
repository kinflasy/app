package br.org.kinflasy.apis.people_filters.entities;

import java.util.List;

import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ConditionGroup extends Condition {

    @ManyToMany
    private List<Condition> filters;

}
