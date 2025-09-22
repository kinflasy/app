package br.org.kinflasy.libs.people_filters.conditions.logical;

import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NegativeCondition extends Condition {

    private final Condition baseFilter;

}
