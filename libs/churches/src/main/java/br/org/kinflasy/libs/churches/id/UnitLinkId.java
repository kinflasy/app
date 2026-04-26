package br.org.kinflasy.libs.churches.id;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class UnitLinkId implements Serializable {

    private UUID unitId;
    private UUID linkId;

}
