package br.org.kinflasy.libs.people_filters.contracts.logical;

import java.util.List;

import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionContract;
import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionContractGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrContractGroup extends ConditionContractGroup {

    public OrContractGroup(final List<ConditionContract> conditions) {
        super(conditions);
    }

}
