package br.org.kinflasy.libs.people.dto;

import java.util.UUID;

import br.org.kinflasy.libs.people.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonSimpleDto {

    private UUID id;
    private String fullName;
    private String nickname;
    private Gender gender;

}
