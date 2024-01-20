package br.org.kinflasy.api.services.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.UserDTO;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.repositories.core.UserRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class UserService extends BaseService<UserRepository, UserDTO, User, Integer> {

    protected UserService(@Autowired final UserRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Integer getId(final @NonNull User user) {
        return user.getId();
    }

    @Override
    public @Nullable UserDTO toNullableDTO(final @Nullable User user) {
        return UserDTO.ofNullable(user);
    }

    @Override
    public @NonNull UserDTO toNonNullDTO(final @NonNull User user) {
        return UserDTO.ofNonNull(user);
    }

}
