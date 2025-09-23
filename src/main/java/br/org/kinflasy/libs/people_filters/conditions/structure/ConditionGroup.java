package br.org.kinflasy.libs.people_filters.conditions.structure;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class ConditionGroup implements Condition {

    private final List<Condition> conditions;

}
