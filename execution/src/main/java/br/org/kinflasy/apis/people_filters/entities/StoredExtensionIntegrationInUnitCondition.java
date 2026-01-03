package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conditions_extension_in_unit", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "unit_id", "extension", "integration_type" })
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class StoredExtensionIntegrationInUnitCondition extends StoredCondition {

    @Column(nullable = false)
    private UUID unitId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Extension extension;

    @Column
    private IntegrationType integrationType;

}
