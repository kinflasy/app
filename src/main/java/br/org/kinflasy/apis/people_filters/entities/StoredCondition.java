package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;
import java.util.function.Predicate;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.people.dto.PersonDto;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public abstract class StoredCondition extends AbstractSimpleAuditable implements Predicate<PersonDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

}
