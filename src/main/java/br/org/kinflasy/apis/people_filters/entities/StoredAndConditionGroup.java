package br.org.kinflasy.apis.people_filters.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "conditions_and_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class StoredAndConditionGroup extends StoredConditionGroup {

}
