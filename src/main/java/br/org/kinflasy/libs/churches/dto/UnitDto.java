package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitDto {

    private UUID id;
    private String name;
    private String slug;
    private String email;
    private String phone;
    private UnitType type;
    private UUID churchId;
    private UUID addressId;

}
