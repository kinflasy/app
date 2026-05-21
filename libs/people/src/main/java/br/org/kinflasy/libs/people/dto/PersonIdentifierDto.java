package br.org.kinflasy.libs.people.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.kinflasy.libs.people.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonIdentifierDto {

    private UUID id;
    private String nickname;
    private Gender gender;
    private UUID profileImageId;

    @JsonIgnore
    private LocalDate birthDate;

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
