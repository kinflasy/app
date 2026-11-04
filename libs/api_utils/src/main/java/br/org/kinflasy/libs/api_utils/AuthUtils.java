package br.org.kinflasy.libs.api_utils;

import java.util.Collections;
import java.util.Optional;

import javax.security.sasl.AuthenticationException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.org.kinflasy.libs.people.dto.UserDto;
import lombok.SneakyThrows;

@Service
public class AuthUtils {

    @SneakyThrows
    public UserDto getLoggedUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(UserDto.class::cast)
                .orElseThrow(AuthenticationException::new);
    }

    @SneakyThrows
    public void runAs(final UserDto user, final Runnable action) {
        final var originalContext = SecurityContextHolder.getContext();
        final var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

        final var tempContext = SecurityContextHolder.createEmptyContext();
        tempContext.setAuthentication(authentication);

        try {
            SecurityContextHolder.setContext(tempContext);
            action.run();
        } finally {
            SecurityContextHolder.setContext(originalContext);
        }
    }

}
