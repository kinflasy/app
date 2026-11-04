package br.org.kinflasy.libs.people.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.kinflasy.libs.contacts.contracts.Emailable;
import br.org.kinflasy.libs.people.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonSubTypes({
        @Type(name = "INACTIVE", value = InactivePersonDto.class),
        @Type(name = "USER", value = UserDto.class) })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class PersonDto implements Emailable {

    private UUID id;
    private String fullName;
    private String nickname;
    private Gender gender;
    private LocalDate birthDate;
    private String phone;
    private UUID addressId;
    private UUID profileImageId;
    private Integer age;

    public int getAge() {
        return Optional.ofNullable(age)
                .or(() -> Optional.ofNullable(birthDate).map(bd -> Period.between(bd, LocalDate.now()).getYears()))
                .orElseThrow();
    }

}
