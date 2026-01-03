package br.org.kinflasy.libs.people.dto;

import java.time.LocalDate;

import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import br.org.kinflasy.libs.people.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonRequest {

    private @NotBlank String fullName;
    private String nickname;
    private @NotNull Gender gender;
    private @NotNull LocalDate birthDate;
    private String phone;
    private AddressRequest address;

}
