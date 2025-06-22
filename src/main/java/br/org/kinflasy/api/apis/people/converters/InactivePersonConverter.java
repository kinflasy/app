package br.org.kinflasy.api.apis.people.converters;

import br.org.kinflasy.api.apis.contacts.converters.AddressConverter;
import br.org.kinflasy.api.apis.people.entities.InactivePerson;
import br.org.kinflasy.api.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.api.libs.people.dto.InactivePersonRequest;

public interface InactivePersonConverter {

    public static InactivePerson toEntity(final InactivePersonDto dto) {
        final var entity = PersonConverter.toEntity(new InactivePerson(), dto);
        entity.setEmail(dto.getEmail());

        return entity;
    }

    public static InactivePerson toEntity(final InactivePersonRequest.Create request) {
        final var entity = new InactivePerson();

        entity.setFullName(request.getFullName());
        entity.setNickname(request.getNickname());
        entity.setGender(request.getGender());
        entity.setBirthDate(request.getBirthDate());
        entity.setPhone(request.getPhone());
        entity.setAddress(AddressConverter.toEntity(request.getAddress()));
        entity.setEmail(request.getEmail());

        return entity;
    }

    public static InactivePerson toEntity(final InactivePersonRequest.Update request) {
        final var entity = toEntity((InactivePersonRequest.Create) request);
        entity.setId(request.getId());
        entity.setEmail(request.getEmail());

        return entity;
    }

    public static InactivePersonDto toDto(final InactivePerson entity) {
        return PersonConverter.toDto(entity, new InactivePersonDto())
                .setEmail(entity.getEmail());
    }

}
