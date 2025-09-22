package br.org.kinflasy.libs.people_filters.contracts.logical;

import java.util.List;

import br.org.kinflasy.libs.people_filters.contracts.structure.Condition;
import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrConditionGroup extends ConditionGroup {

    public OrConditionGroup(final List<Condition> conditions) {
        super(conditions);
    }

}
