package br.org.kinflasy.libs.churches.enums.membership;

import java.util.Set;

import br.org.kinflasy.libs.lib_utils.contracts.HierarchyEnum;
import lombok.Getter;

@Getter
public enum Affiliation implements HierarchyEnum<Affiliation> {

    VISITOR,
    CONGREGATED(VISITOR),
    MEMBER(CONGREGATED);

    private final Set<Affiliation> includedValues;

    private Affiliation(final Affiliation... values) {
        this.includedValues = flatIncludedValues(values);
    }

}
