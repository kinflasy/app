package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import br.org.kinflasy.api.apis.people.entities.InactivePerson;
import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.libs.people.enums.Gender;
import lombok.Getter;

@Getter
public class CreateInactivePerson extends CreatePerson {

    protected String email;

    public CreateInactivePerson(String name, String nickname, Gender gender,
            LocalDate birthDate, String phone, CreateAddress address, String email) {
        super(name, nickname, gender, birthDate, phone, address);
        this.email = email;
    }

    public InactivePerson update(final InactivePerson person) {

        super.update(person);
        person.setEmail(email);

        return person;
        
    }

    public InactivePerson toInactivePerson() {
        return update(new InactivePerson());
    }
    
}
