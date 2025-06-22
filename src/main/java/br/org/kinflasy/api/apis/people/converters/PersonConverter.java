package br.org.kinflasy.api.apis.people.converters;

import br.org.kinflasy.api.apis.contacts.converters.AddressConverter;
import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.libs.people.dto.PersonDto;

public interface PersonConverter {

    public static PersonDto toDto(final Person entity) {
        return toDto(entity, new PersonDto());
    }

    public static <E extends Person> E toEntity(final E entity, final PersonDto dto) {
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setNickname(dto.getNickname());
        entity.setGender(dto.getGender());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPhone(dto.getPhone());
        entity.setAddress(AddressConverter.toEntity(dto.getAddress()));

        return entity;
    }

    public static <D extends PersonDto> D toDto(final Person entity, final D dto) {
        dto.setId(entity.getId())
                .setFullName(entity.getFullName())
                .setNickname(entity.getNickname())
                .setGender(entity.getGender())
                .setBirthDate(entity.getBirthDate())
                .setPhone(entity.getPhone())
                .setAddress(AddressConverter.toDto(entity.getAddress()));

        return dto;
    }

}
