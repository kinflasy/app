package br.org.kinflasy.apis.churches.entities.department;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "extensions_subscriptions", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "departmentId", "extension" }) })
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = true)
public class ExtensionSubscription extends AbstractSimpleAuditable {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Chaves "estrangeiras" (referência)
     */

    @Column(nullable = false)
    private UUID departmentId;

    /*
     * Enumerações
     */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Extension extension;

}
