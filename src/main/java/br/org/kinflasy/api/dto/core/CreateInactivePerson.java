package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.InactivePerson;
import br.org.kinflasy.api.utils.enums.core.Gender;
import lombok.Getter;

@Getter
public class CreateInactivePerson extends CreatePerson {

    protected @Nullable String email;

    public CreateInactivePerson(String name, @Nullable String nickname, Gender gender,
            LocalDate birthDate, @Nullable String phone, @Nullable CreateAddress address, String email) {
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
