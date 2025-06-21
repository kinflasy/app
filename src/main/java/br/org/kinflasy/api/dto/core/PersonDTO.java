package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.utils.enums.core.Gender;

public record PersonDTO(
        Integer id,
        String name,
        @Nullable String nickname,
        Gender gender,
        LocalDate birthDate,
        @Nullable String phone,
        @Nullable String email,
        @Nullable AddressDTO address) {

    public static @Nullable PersonDTO ofNullable(@Nullable Person person) {
        return (person != null) ? ofNonNull(person) : null;
    }

    public static PersonDTO ofNonNull(Person person) {
        return new PersonDTO(person.getId(), person.getFullName(), person.getNickname(), person.getGender(),
                person.getBirthDate(), person.getPhone(), person.getEmail(),
                AddressDTO.ofNullable(person.getAddress()));
    }

}
