package br.org.kinflasy.apis.people_filters.predicates.structure;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Component
public abstract class ConditionPredicateGroup extends ConditionPredicate {

    private final List<ConditionPredicate> predicates;

}
