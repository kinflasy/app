package br.org.kinflasy.api.apis.people.converters;

import br.org.kinflasy.api.apis.contacts.converters.AddressConverter;
import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.libs.people.dto.UserDto;
import br.org.kinflasy.api.libs.people.dto.UserRequest;

public interface UserConverter {

    public static User toEntity(final UserDto dto) {
        final var entity = PersonConverter.toEntity(new User(), dto);

        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setEmailVerifiedAt(dto.getEmailVerifiedAt());
        entity.setPassword(dto.getPassword());

        return entity;
    }

    public static User toEntity(final UserRequest.Create request) {
        final var entity = new User();

        entity.setFullName(request.getFullName());
        entity.setNickname(request.getNickname());
        entity.setGender(request.getGender());
        entity.setBirthDate(request.getBirthDate());
        entity.setPhone(request.getPhone());
        entity.setAddress(AddressConverter.toEntity(request.getAddress()));
        entity.setUsername(request.getUsername());
        entity.setEmail(request.getEmail());
        entity.setEmailVerifiedAt(request.getEmailVerifiedAt());
        entity.setPassword(request.getPassword());

        return entity;
    }

    public static User toEntity(final UserRequest.Update request) {
        final var entity = toEntity((UserRequest.Create) request);
        entity.setId(request.getId());
        entity.setUsername(request.getUsername());
        entity.setEmail(request.getEmail());
        entity.setEmailVerifiedAt(request.getEmailVerifiedAt());
        entity.setPassword(request.getPassword());

        return entity;
    }

    public static UserDto toDto(final User entity) {
        return PersonConverter.toDto(entity, new UserDto())
                .setUsername(entity.getUsername())
                .setEmail(entity.getEmail())
                .setEmailVerifiedAt(entity.getEmailVerifiedAt())
                .setPassword(entity.getPassword());
    }

}
