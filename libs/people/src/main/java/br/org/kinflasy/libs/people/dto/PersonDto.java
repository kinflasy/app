package br.org.kinflasy.libs.people.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.kinflasy.libs.people.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonSubTypes({
        @Type(name = "INACTIVE", value = InactivePersonDto.class),
        @Type(name = "USER", value = UserDto.class) })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class PersonDto {

    private UUID id;
    private String fullName;
    private String nickname;
    private Gender gender;
    private LocalDate birthDate;
    private String phone;
    private UUID addressId;
    private UUID profileImageId;

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
