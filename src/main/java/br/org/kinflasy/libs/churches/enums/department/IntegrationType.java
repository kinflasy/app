package br.org.kinflasy.libs.churches.enums.department;

import java.util.Set;

import br.org.kinflasy.libs.lib_utils.contracts.HierarchyEnum;
import lombok.Getter;

@Getter
public enum IntegrationType implements HierarchyEnum<IntegrationType> {

    OBSERVER,
    CONSULTANT(OBSERVER),
    INTEGRANT(OBSERVER),
    ASSISTANT(INTEGRANT),
    LEADER(ASSISTANT);

    private final Set<IntegrationType> includedValues;

    private IntegrationType(final IntegrationType... values) {
        this.includedValues = flatIncludedValues(values);
    }

}
