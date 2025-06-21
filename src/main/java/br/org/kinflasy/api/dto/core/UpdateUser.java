package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.utils.enums.core.Gender;
import lombok.Getter;

@Getter
public class UpdateUser extends UpdatePerson {

    private @Nullable String username;
    private @Nullable String email;

    public UpdateUser(@Nullable String name, @Nullable String nickname, @Nullable Gender gender,
            @Nullable LocalDate birthDate, @Nullable String phone, @Nullable CreateAddress address,
            @Nullable String username, @Nullable String email) {

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
