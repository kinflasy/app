package br.org.kinflasy.api.dto.core;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.libs.people.enums.Gender;

public record InactivePersonDTO(
        UUID id,
        String name,
        String nickname,
        Gender gender,
        LocalDate birthDate,
        String phone,
        String email,
        AddressDTO address) {

    public static InactivePersonDTO ofNullable(Person person) {
        return (person != null) ? ofNonNull(person) : null;
    }

    public static InactivePersonDTO ofNonNull(Person person) {
        return new InactivePersonDTO(person.getId(), person.getFullName(), person.getNickname(), person.getGender(),
                person.getBirthDate(), person.getPhone(), person.getEmail(),
                AddressDTO.ofNullable(person.getAddress()));
    }

}
