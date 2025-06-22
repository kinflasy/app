package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.libs.people.enums.Gender;
import lombok.Getter;

@Getter
public class CreateUser extends CreatePerson {

    private String username;
    private String email;
    private String password;

    public CreateUser(String name, String nickname, Gender gender,
            LocalDate birthDate, String phone, CreateAddress address,
            String username, String email, String password) {

        super(name, nickname, gender, birthDate, phone, address);
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public User update(final User user) {
        super.update(user);

        user.setUsername(username);
        user.setEmail(email);
        user.setEmailVerifiedAt(null);
        user.setPassword(password);

        return user;
    }

    public User toUser() {
        return update(new User());
    }

}
