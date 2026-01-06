package br.org.kinflasy.apis.people_filters.entities;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class StoredConditionGroup extends StoredCondition {

    public abstract List<StoredCondition> getConditions();

}
