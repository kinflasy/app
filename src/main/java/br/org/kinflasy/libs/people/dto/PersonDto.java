package br.org.kinflasy.libs.people.dto;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.libs.people.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class PersonDto {

    private UUID id;
    private String fullName;
    private String nickname;
    private Gender gender;
    private LocalDate birthDate;
    private String phone;
    private UUID addressId;

}
