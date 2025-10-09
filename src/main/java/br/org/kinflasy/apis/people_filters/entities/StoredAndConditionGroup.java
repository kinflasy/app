package br.org.kinflasy.apis.people_filters.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conditions_and_group")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoredAndConditionGroup extends StoredConditionGroup {

}
