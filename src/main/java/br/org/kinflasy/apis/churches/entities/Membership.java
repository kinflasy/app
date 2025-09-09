package br.org.kinflasy.apis.churches.entities;

import java.time.LocalDate;
import java.util.UUID;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "memberships", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "unit_id", "person_id" })
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Membership extends AbstractSimpleAuditable<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated
    @Column(nullable = false)
    private Affiliation status;

    @ManyToOne(optional = false)
    private Unit unit;

    @Column(nullable = false)
    private UUID personId;

    @Enumerated(EnumType.ORDINAL)
    private EntryMode entryMode;

    @Column
    private LocalDate entryDate;

    @Enumerated(EnumType.ORDINAL)
    private LeaveMode leaveMode;

    @Column
    private LocalDate leaveDate;

    @Column
    private String leaveNote;

}
