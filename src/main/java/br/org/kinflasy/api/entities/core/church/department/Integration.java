package br.org.kinflasy.api.entities.core.church.department;

import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.utils.enums.core.church.department.IntegrationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "integrations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"department_id", "person_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class Integration extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Person person;

    @Column(nullable = false)
    private IntegrationType type = IntegrationType.INTEGRANT;

}
