package br.org.kinflasy.apis.calendar.entities;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "event_collaborations", uniqueConstraints = {
        @UniqueConstraint(columnNames = { EventCollaboration_.CALENDAR_EVENT_ID, EventCollaboration_.DEPARTMENT_ID })
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class EventCollaboration {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Chaves "estrangeiras" (referências)
     */

    @Column
    private UUID calendarEventId;

    @Column
    private UUID departmentId;

}
