package br.org.kinflasy.api.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.api.libs.churches.enums.UnitType;
import br.org.kinflasy.api.libs.contacts.dto.AddressDto;
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
    private ChurchDto church;
    private AddressDto address;

}
