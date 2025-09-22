package br.org.kinflasy.libs.people_filters.contracts.logical;

import br.org.kinflasy.libs.people_filters.contracts.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NegativeCondition extends Condition {

    private final Condition baseFilter;

}
