package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.contacts.dto.AddressDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    private UUID profileImageId;
    private UUID coverImageId;

    @Data
    public static class Clean {
        private UUID id;
        private String name;
        private String slug;
        private UUID churchId;

    }

    @Data
    public static class CleanWithChurch {
        private UUID id;
        private String name;
        private String slug;
        private ChurchDto.Clean church;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Detailed extends UnitDto {
        private ChurchDto church;
        private AddressDto address;
    }

}
