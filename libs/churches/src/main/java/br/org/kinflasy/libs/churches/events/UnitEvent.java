package br.org.kinflasy.libs.churches.events;

import br.org.kinflasy.libs.churches.dto.UnitDto;
import lombok.Data;

public interface UnitEvent {

    public UnitDto getUnit();

    @Data
    public static class Created implements UnitEvent {
        private final UnitDto unit;
    }

}
