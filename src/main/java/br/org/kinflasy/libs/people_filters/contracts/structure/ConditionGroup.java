package br.org.kinflasy.libs.people_filters.contracts.structure;

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
