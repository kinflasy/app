package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.utils.enums.core.Gender;

public record PersonDTO(
        @NonNull Integer id,
        @NonNull String name,
        @Nullable String nickname,
        @NonNull Gender gender,
        @NonNull LocalDate birthDate,
        @Nullable String phone,
        @Nullable AddressDTO address) {

    public static @NonNull PersonDTO of(@NonNull Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getNickname(), person.getGender(),
                person.getBirthDate(), person.getPhone(), AddressDTO.of(person.getAddress()));
    }

}
