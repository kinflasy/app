package br.org.kinflasy.apis.churches.entities;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import br.org.kinflasy.libs.churches.id.UnitLinkId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@IdClass(UnitLinkId.class)
@DynamicUpdate
@Table(name = "unit_links", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "unit_id", "link_id" })
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class UnitLink {

    @Id
    @Column(nullable = false)
    private UUID unitId;
    
    @Id
    @Column(nullable = false)
    private UUID linkId;

}
