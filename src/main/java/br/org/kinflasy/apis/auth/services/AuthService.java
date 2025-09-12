package br.org.kinflasy.apis.auth.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.auth.dto.AuthUser;
import br.org.kinflasy.clients.UserClient;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return (AuthUser) userClient.findByUsername(username);
    }

}
