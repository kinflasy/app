package br.org.kinflasy.libs.people.dto;

import br.org.kinflasy.libs.contacts.dto.EmailAddressDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends PersonDto {

    private String username;
    private EmailAddressDto email;

}
