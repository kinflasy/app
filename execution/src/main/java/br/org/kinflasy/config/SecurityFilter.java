package br.org.kinflasy.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.org.kinflasy.apis.auth.services.AccessTokenService;
import br.org.kinflasy.apis.auth.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private AccessTokenService accessTokenService;
    private UserService userService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {
        recoverToken(request)
                .ifPresent(token -> {
                    final var username = accessTokenService.validateToken(token);
                    final var user = userService.findByUsername(username);

                    final var authentication = new UsernamePasswordAuthenticationToken(user, null,
                            Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });

        filterChain.doFilter(request, response);
    }

    private Optional<String> recoverToken(final HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(authorization -> authorization.replace("Bearer ", ""));
    }

}
