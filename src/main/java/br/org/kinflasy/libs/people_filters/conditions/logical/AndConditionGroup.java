package br.org.kinflasy.libs.people_filters.conditions.logical;

import java.util.List;

import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.conditions.structure.ConditionGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AndConditionGroup extends ConditionGroup {

    public AndConditionGroup(final List<Condition> conditions) {
        super(conditions);
    }

}
