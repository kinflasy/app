package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.utils.enums.core.Gender;

public record InactivePersonDTO(
        @NonNull Integer id,
        @NonNull String name,
        @Nullable String nickname,
        @NonNull Gender gender,
        @NonNull LocalDate birthDate,
        @Nullable String phone,
        @Nullable String email,
        @Nullable AddressDTO address) {

    public static @Nullable InactivePersonDTO ofNullable(@Nullable Person person) {
        return (person != null) ? ofNonNull(person) : null;
    }

    public static @NonNull InactivePersonDTO ofNonNull(@NonNull Person person) {
        return new InactivePersonDTO(person.getId(), person.getFullName(), person.getNickname(), person.getGender(),
                person.getBirthDate(), person.getPhone(), person.getEmail(),
                AddressDTO.ofNullable(person.getAddress()));
    }

}
