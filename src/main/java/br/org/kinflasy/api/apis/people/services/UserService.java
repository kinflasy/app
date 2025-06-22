package br.org.kinflasy.api.apis.people.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.apis.people.repositories.UserRepository;
import br.org.kinflasy.api.dto.core.UserDTO;
import br.org.kinflasy.api.services.BaseService;

@Service
public class UserService extends BaseService<UserRepository, UserDTO, User, UUID> {

    protected UserService(@Autowired final UserRepository repository) {
        super(repository);
    }

    @Override
    public UUID getId(final User user) {
        return user.getId();
    }

    @Override
    public UserDTO toDto(final User user) {
        return UserDTO.ofNonNull(user);
    }

}
