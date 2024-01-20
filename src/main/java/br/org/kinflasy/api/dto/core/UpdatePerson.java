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
public class UpdatePerson {

    @Nullable
    protected String name;

    @Nullable
    protected String nickname;

    @Nullable
    protected Gender gender;

    @Nullable
    protected LocalDate birthDate;

    @Nullable
    protected String phone;

    @Nullable
    protected CreateAddress address;

    public @NonNull Person transferTo(@NonNull final Person person) {
        if (name != null) {
            person.setName(name);
        }

        if (nickname != null) {
            person.setNickname(nickname);
        }

        if (gender != null) {
            person.setGender(gender);
        }

        if (birthDate != null) {
            person.setBirthDate(birthDate);
        }

        if (phone != null) {
            person.setPhone(phone);
        }

        person.setAddress((address != null) ? address.toAddress() : null);
        return person;
    }

}
