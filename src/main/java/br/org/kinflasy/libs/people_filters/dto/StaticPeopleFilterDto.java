package br.org.kinflasy.libs.people_filters.dto;

import java.util.UUID;

import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaticPeopleFilterDto {

    private UUID id;
    private PersonCharacteristic characteristic;

}
