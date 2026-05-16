package br.org.kinflasy.apis.people.adapters;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import br.org.kinflasy.apis.people.entities.Person;
import br.org.kinflasy.libs.people.dto.PersonIdentifierDto;

public class PersonIdentifierDtoAdapter extends PersonIdentifierDto {

    public static <E extends Person, D extends PersonIdentifierDto> void map(final E entity, final D dto) {
        final var nickname = Optional.ofNullable(entity.getNickname())
                .filter(StringUtils::isNotBlank)
                .orElseGet(() -> entity.getFullName().split(" ")[0]);

        dto.setId(entity.getId());
        dto.setNickname(nickname);
        dto.setGender(entity.getGender());
    }

    public PersonIdentifierDtoAdapter(final Person entity) {
        map(entity, this);
    }

    public PersonIdentifierDto asSuper() {
        return this;
    }

}
