package br.org.kinflasy.libs.people_filters.conditions.structure;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public abstract class ConditionGroup implements Condition {

    private final List<Condition> conditions = new ArrayList<>();

}
