package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.contacts.contracts.Emailable;
import br.org.kinflasy.libs.contacts.dto.AddressDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UnitDto implements Emailable {

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
    @Accessors(chain = true)
    public static class Clean {
        private UUID id;
        private String name;
        private String slug;
        private UUID churchId;

    }

    @Data
    @Accessors(chain = true)
    public static class CleanWithChurch {
        private UUID id;
        private String name;
        private String slug;
        private ChurchDto.Clean church;
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Detailed extends UnitDto {
        private ChurchDto church;
        private AddressDto address;
    }

}
