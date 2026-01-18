package br.org.kinflasy.libs.api_utils;

import java.util.Optional;

import javax.security.sasl.AuthenticationException;

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

}
