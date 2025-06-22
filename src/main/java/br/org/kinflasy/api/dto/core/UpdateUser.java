package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.libs.people.enums.Gender;
import lombok.Getter;

@Getter
public class UpdateUser extends UpdatePerson {

    private String username;
    private String email;

    public UpdateUser(String name, String nickname, Gender gender,
            LocalDate birthDate, String phone, CreateAddress address,
            String username, String email) {

        super(name, nickname, gender, birthDate, phone, address);
        this.username = username;
        this.email = email;

    }

    public User update(final User user) {
        super.update(user);

        if (username != null) {
            user.setUsername(username);
        }

        if (email != null) {
            user.setEmail(email);
        }

        user.setAddress((address != null) ? address.toAddress() : null);

        return user;
    }

}
