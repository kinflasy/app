package br.org.kinflasy.libs.churches.id;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UnitLinkId implements Serializable {

    private UUID unitId;
    private UUID linkId;

}
