package br.org.kinflasy.apis.calendar.entities.scales;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "owner_scales", indexes = {
        @Index(columnList = OwnerScale_.CALENDAR_EVENT_ID)
})
@DynamicUpdate
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = true)
public class OwnerScale extends Scale {

    /*
     * Chaves "estrangeiras" (referências)
     */
    @Column(nullable = false)
    private UUID calendarEventId;

}
