package br.org.kinflasy.libs.base_conditions.conditions.logical;

import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NegativeCondition implements Condition {

    private final Condition baseCondition;

}
