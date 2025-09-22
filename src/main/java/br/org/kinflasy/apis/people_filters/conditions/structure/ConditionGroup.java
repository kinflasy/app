package br.org.kinflasy.apis.people_filters.conditions.structure;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class ConditionGroup extends Condition {

    private final List<Condition> conditions;

}
