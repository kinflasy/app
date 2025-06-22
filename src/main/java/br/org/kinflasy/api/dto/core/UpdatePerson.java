package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.libs.people.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdatePerson {
    
    protected String name;
    protected String nickname;
    protected Gender gender;
    protected LocalDate birthDate;
    protected String phone;
    protected CreateAddress address;

    public Person update(final Person person) {
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
