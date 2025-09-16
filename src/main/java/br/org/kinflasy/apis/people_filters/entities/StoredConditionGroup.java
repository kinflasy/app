package br.org.kinflasy.apis.people_filters.entities;

import java.util.List;

import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class StoredConditionGroup extends StoredCondition {

    @ManyToMany
    private List<StoredCondition> filters;

}
