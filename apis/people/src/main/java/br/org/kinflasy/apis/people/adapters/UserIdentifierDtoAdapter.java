package br.org.kinflasy.apis.people.adapters;

import br.org.kinflasy.apis.people.entities.User;
import br.org.kinflasy.libs.people.dto.UserIdentifierDto;

public class UserIdentifierDtoAdapter extends UserIdentifierDto {

    public UserIdentifierDtoAdapter(final User entity) {
        PersonIdentifierDtoAdapter.map(entity, this);
        setUsername(entity.getUsername());
    }

    public UserIdentifierDto asSuper() {
        return this;
    }

}
