package br.org.kinflasy.apis.churches.entities.department;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "unit_lineups")
@DynamicUpdate
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = true)
public class UnitLineup extends Lineup {

    /*
     * Chaves "estrangeiras" (referências)
     */

    @Column(nullable = false)
    private UUID unitId;

}
