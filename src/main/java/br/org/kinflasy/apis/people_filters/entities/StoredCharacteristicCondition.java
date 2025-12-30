package br.org.kinflasy.apis.people_filters.entities;

import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conditions_characteristic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoredCharacteristicCondition extends StoredCondition {

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private PersonCharacteristic characteristic;

}
