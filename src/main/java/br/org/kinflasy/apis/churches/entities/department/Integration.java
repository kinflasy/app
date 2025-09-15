package br.org.kinflasy.apis.churches.entities.department;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@DynamicUpdate
@Table(name = "integrations", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "department_id", "person_id" })
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Integration extends AbstractSimpleAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private IntegrationType type = IntegrationType.INTEGRANT;

    @ManyToOne(optional = false)
    private Department department;

    @Column(nullable = false)
    private UUID personId;

}
