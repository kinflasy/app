package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.utils.enums.core.Gender;

public record UserDTO(
        @NonNull Integer id,
        @NonNull String username,
        @NonNull String email,
        @NonNull String name,
        @Nullable String nickname,
        @NonNull Gender gender,
        @NonNull LocalDate birthDate,
        @Nullable String phone,
        @Nullable AddressDTO address) {

    public static @Nullable UserDTO ofNullable(final @Nullable User user) {
        return (user != null) ? ofNonNull(user) : null;
    }

    public static @NonNull UserDTO ofNonNull(final @NonNull User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getNickname(),
                user.getGender(), user.getBirthDate(), user.getPhone(), AddressDTO.ofNullable(user.getAddress()));
    }

}
