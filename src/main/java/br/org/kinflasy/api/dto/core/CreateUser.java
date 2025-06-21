package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.utils.enums.core.Gender;
import lombok.Getter;

@Getter
public class CreateUser extends CreatePerson {

    private String username;
    private String email;
    private String password;

    public CreateUser(String name, @Nullable String nickname, Gender gender,
            LocalDate birthDate, @Nullable String phone, @Nullable CreateAddress address,
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
