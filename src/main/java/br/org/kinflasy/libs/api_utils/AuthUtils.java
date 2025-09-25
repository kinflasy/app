package br.org.kinflasy.libs.api_utils;

import java.util.Collections;
import java.util.function.Supplier;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.org.kinflasy.libs.people.dto.UserDto;

@Service
public class AuthUtils {

    public UserDto getLoggedUser() {
        return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public <T> T runAsSystem(final Supplier<T> supplier) {
        // Guardar o usuário logado
        final var loggedAuthentication = SecurityContextHolder.getContext().getAuthentication();

        // Gerar usuário do sistema
        final var systemUser = new UserDto()
                .setUsername("system-user")
                .setId(getLoggedUser().getId());
        final var systemAuthentication = new UsernamePasswordAuthenticationToken(systemUser, "vazio",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));

        // Autenticar com usuário do sistema
        SecurityContextHolder.getContext().setAuthentication(systemAuthentication);

        try {
            // Executar ações
            return supplier.get();
        } finally {
            // Devolver autenticação ao usuário logado
            SecurityContextHolder.getContext().setAuthentication(loggedAuthentication);
        }
    }

    public void runAsSystem(final Runnable runnable) {
        runAsSystem(() -> {
            runnable.run();
            return null;
        });
    }

}
