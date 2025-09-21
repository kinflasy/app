package br.org.kinflasy.libs.people_filters.contracts.logical;

import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionContract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NegativeContract extends ConditionContract {

    private final ConditionContract baseFilter;

}
