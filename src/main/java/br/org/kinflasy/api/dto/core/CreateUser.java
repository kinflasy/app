package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.utils.enums.core.Gender;
import lombok.Getter;

@Getter
public class CreateUser extends CreatePerson {

    private @NonNull String username;
    private @NonNull String email;
    private @NonNull String password;

    public CreateUser(@NonNull String name, @Nullable String nickname, @NonNull Gender gender,
            @NonNull LocalDate birthDate, @Nullable String phone, @Nullable CreateAddress address,
            @NonNull String username, @NonNull String email, @NonNull String password) {

        super(name, nickname, gender, birthDate, email, phone, address);
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public @NonNull User update(final @NonNull User user) {
        super.update(user);

        user.setUsername(username);
        user.setEmail(email);
        user.setEmailVerifiedAt(null);
        user.setPassword(password);

        return user;
    }

    public @NonNull User toUser() {
        return update(new User());
    }

}
