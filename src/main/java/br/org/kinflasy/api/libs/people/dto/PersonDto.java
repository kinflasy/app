package br.org.kinflasy.api.libs.people.dto;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.api.libs.contacts.dto.AddressDto;
import br.org.kinflasy.api.libs.people.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDto {

    private UUID id;
    private String fullName;
    private String nickname;
    private Gender gender;
    private LocalDate birthDate;
    private String phone;
    private AddressDto address;

}
