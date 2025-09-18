package br.org.kinflasy.libs.api_utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.org.kinflasy.libs.people.dto.UserDto;

@Service
public class AuthUtils {

    public UserDto getLoggedUser() {
        return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
