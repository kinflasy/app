package br.org.kinflasy.entities;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "unit_calendar_events")
@DynamicUpdate
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = true)
public class UnitCalendarEvent extends CalendarEvent {

    /*
     * Chaves "estrangeiras" (referências)
     */

    @Column(nullable = false)
    private UUID unitId;

}
