package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public abstract class StoredCondition extends AbstractSimpleAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

}
