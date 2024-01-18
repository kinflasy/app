package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.utils.enums.core.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePerson {

    @NonNull
    private String name;

    @Nullable
    private String nickname;

    @NonNull
    private Gender gender;

    @NonNull
    private LocalDate birthDate;

    @Nullable
    private String phone;

    @Nullable
    private CreateAddress address;

    public @NonNull Person toPerson() {
        final var person = new Person();
        person.setName(name);
        person.setNickname(nickname);
        person.setGender(gender);
        person.setBirthDate(birthDate);
        person.setPhone(phone);
        person.setAddress((address != null) ? address.toAddress() : null);
        return person;
    }

}
