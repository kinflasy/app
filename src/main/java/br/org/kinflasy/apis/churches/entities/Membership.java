package br.org.kinflasy.apis.churches.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.churches.enums.membership.EntryMode;
import br.org.kinflasy.libs.churches.enums.membership.LeaveMode;
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
@Table(name = "memberships")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Membership extends AbstractSimpleAuditable {

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

    @Enumerated
    @Column(nullable = false)
    private Affiliation affiliation;

    @Enumerated(EnumType.ORDINAL)
    private EntryMode entryMode;

    @Enumerated(EnumType.ORDINAL)
    private LeaveMode leaveMode;

    /*
     * Dados primitivos
     */

    @Column
    private LocalDate entryDate;

    @Column
    private LocalDate leaveDate;

    @Column
    private String leaveNote;

}
