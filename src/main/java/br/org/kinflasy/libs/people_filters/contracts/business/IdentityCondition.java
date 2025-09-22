package br.org.kinflasy.libs.people_filters.contracts.business;

import java.util.UUID;

import br.org.kinflasy.libs.people_filters.contracts.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IdentityCondition extends Condition {

    private final UUID personId;

}
