package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;


import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.utils.enums.core.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePerson {

    protected String name;
    protected String nickname;
    protected Gender gender;
    protected LocalDate birthDate;
    protected String phone;
    protected CreateAddress address;

    public Person update(final Person person) {
        person.setFullName(name);
        person.setNickname(nickname);
        person.setGender(gender);
        person.setBirthDate(birthDate);
        person.setPhone(phone);
        person.setAddress((address != null) ? address.toAddress() : null);

        return person;
    }
    
}
