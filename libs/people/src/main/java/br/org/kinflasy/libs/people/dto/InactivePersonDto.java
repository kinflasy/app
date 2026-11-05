package br.org.kinflasy.libs.people.dto;

import java.util.UUID;

import br.org.kinflasy.libs.contacts.dto.EmailAddressDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InactivePersonDto extends PersonDto {

    private UUID churchId;
    private EmailAddressDto email;

}
