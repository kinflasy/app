package br.org.kinflasy.apis.calendar.entities.scales;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "scale_items", uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = { ScaleItem_.SCALE_ID, ScaleItem_.ROLE_ID,
                ScaleItem_.PERSON_ID })
}, indexes = {
        @Index(columnList = ScaleItem_.SCALE_ID),
        @Index(columnList = ScaleItem_.PERSON_ID)
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class ScaleItem {

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
    private UUID scaleId;

    @Column(nullable = false)
    private UUID roleId;

    @Column(nullable = false)
    private UUID personId;

}
