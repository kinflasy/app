package br.org.kinflasy.libs.people_filters.conditions.business;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.Value;

@Value
public class ExtensionIntegrantInChurchCondition implements Condition {

    private final UUID churchId;
    private final Extension extension;
    private final IntegrationType type;

}
