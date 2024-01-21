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

    protected @NonNull String name;
    protected @Nullable String nickname;
    protected @NonNull Gender gender;
    protected @NonNull LocalDate birthDate;
    protected @Nullable String email;
    protected @Nullable String phone;
    protected @Nullable CreateAddress address;

    public @NonNull Person update(final @NonNull Person person) {
        person.setName(name);
        person.setNickname(nickname);
        person.setGender(gender);
        person.setBirthDate(birthDate);
        person.setPhone(phone);
        person.setAddress((address != null) ? address.toAddress() : null);

        return person;
    }

    public @NonNull Person toPerson() {
        return update(new Person());
    }
    
}
