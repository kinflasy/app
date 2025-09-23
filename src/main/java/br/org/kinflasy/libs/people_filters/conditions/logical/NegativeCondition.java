package br.org.kinflasy.libs.people_filters.conditions.logical;

import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NegativeCondition implements Condition {

    private final Condition baseCondition;

}
