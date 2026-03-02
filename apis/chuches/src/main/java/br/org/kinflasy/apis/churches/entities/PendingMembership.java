package br.org.kinflasy.apis.churches.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.churches.enums.membership.EntryMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "pending_memberships")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class PendingMembership extends AbstractSimpleAuditable {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Chaves "estrangeiras" (referências)
     */

    @Column(nullable = false)
    private UUID unitId;

    @Column(nullable = false)
    private UUID personId;

    /*
     * Enumerações
     */

    @Column
    @Enumerated(EnumType.STRING)
    private Affiliation affiliation;

    @Enumerated(EnumType.STRING)
    private EntryMode entryMode;

    /*
     * Dados primitivos
     */

    @Column
    private LocalDate entryDate;

    @Column
    private LocalDate unitConfirmationDate;

    @Column
    private LocalDate userConfirmationDate;

}
