package br.org.kinflasy.apis.people_filters.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conditions_negative")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoredNegativeCondition extends StoredCondition {

    @ManyToOne(optional = false)
    private StoredCondition baseCondition;

}
