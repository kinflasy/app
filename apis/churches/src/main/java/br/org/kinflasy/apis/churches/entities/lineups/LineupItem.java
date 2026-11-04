package br.org.kinflasy.apis.churches.entities.lineups;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "lineup_items")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class LineupItem {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Chaves estrangeiras (referências)
     */

    @Column(nullable = false, updatable = false)
    private UUID lineupId;

    @Column(nullable = false, updatable = false)
    private UUID roleId;

    @Column(nullable = false)
    private String description;

}
