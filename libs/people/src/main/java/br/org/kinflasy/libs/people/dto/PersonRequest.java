package br.org.kinflasy.libs.people.dto;

import java.time.LocalDate;

import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import br.org.kinflasy.libs.people.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonRequest {

    @NotBlank
    private String fullName;

    private String nickname;

    @NotNull
    private Gender gender;

    @NotNull
    @PastOrPresent
    private LocalDate birthDate;

    private String phone;

    private AddressRequest address;

}
