package br.org.kinflasy.api.apis.churches.entities.department;

import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.libs.churches.enums.department.IntegrationType;
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
@Table(name = "integrations", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "department_id", "person_id" })
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Integration extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private IntegrationType type = IntegrationType.INTEGRANT;

    @ManyToOne(optional = false)
    private Department department;

    @ManyToOne(optional = false)
    private Person person;

}
