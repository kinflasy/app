package br.org.kinflasy.apis.people_filters.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "conditions")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public abstract class StoredCondition extends AbstractSimpleAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @ManyToMany(mappedBy = "conditions", fetch = FetchType.LAZY)
    private List<StoredAndConditionGroup> andGroups = new ArrayList<>();

    @ManyToMany(mappedBy = "conditions", fetch = FetchType.LAZY)
    private List<StoredOrConditionGroup> orGroups = new ArrayList<>();

}
