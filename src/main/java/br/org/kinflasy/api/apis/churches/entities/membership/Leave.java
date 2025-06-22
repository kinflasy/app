package br.org.kinflasy.api.apis.churches.entities.membership;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.libs.churches.enums.membership.LeaveType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "leaves")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Leave extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private LeaveType type;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String note;

    @ManyToOne(optional = false)
    private Membership membership;

}
