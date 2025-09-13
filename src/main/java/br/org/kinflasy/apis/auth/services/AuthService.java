package br.org.kinflasy.apis.auth.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.auth.dto.AuthUser;
import br.org.kinflasy.clients.UserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserClient userClient;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var userDto = userClient.findByUsername(username);
        log.info("user found:\nusername:{}, password:{}", userDto.getUsername(), userDto.getPassword());

        final var userDto2 = mapper.map(userDto, AuthUser.class);
        log.info("user cast:\nusername:{}, password:{}, type:{}", userDto2.getUsername(), userDto2.getPassword(),
                userDto2.getClass().getName());
        return userDto2;
    }

}
