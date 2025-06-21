package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.utils.enums.core.Gender;

public record UserDTO(
        Integer id,
        String username,
        String email,
        String name,
        @Nullable String nickname,
        Gender gender,
        LocalDate birthDate,
        @Nullable String phone,
        @Nullable AddressDTO address) {

    public static @Nullable UserDTO ofNullable(final @Nullable User user) {
        return (user != null) ? ofNonNull(user) : null;
    }

    public static UserDTO ofNonNull(final User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getNickname(),
                user.getGender(), user.getBirthDate(), user.getPhone(), AddressDTO.ofNullable(user.getAddress()));
    }

}
