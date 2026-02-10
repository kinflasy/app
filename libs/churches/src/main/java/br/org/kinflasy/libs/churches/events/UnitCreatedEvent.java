package br.org.kinflasy.libs.churches.events;

import br.org.kinflasy.libs.churches.dto.UnitDto;
import lombok.Data;

@Data
public class UnitCreatedEvent {

    private final UnitDto unit;

}
