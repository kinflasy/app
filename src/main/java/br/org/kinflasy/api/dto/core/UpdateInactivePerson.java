package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.InactivePerson;
import br.org.kinflasy.api.utils.enums.core.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateInactivePerson {
    
    protected @Nullable String name;
    protected @Nullable String nickname;
    protected @Nullable Gender gender;
    protected @Nullable LocalDate birthDate;
    protected @Nullable String phone;
    protected @Nullable CreateAddress address;

    public InactivePerson update(final InactivePerson person) {
        if (name != null) {
            person.setFullName(name);
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
