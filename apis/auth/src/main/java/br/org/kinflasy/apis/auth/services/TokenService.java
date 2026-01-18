package br.org.kinflasy.apis.auth.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.org.kinflasy.libs.people.dto.UserDto;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(final UserDto user) {
        return JWT.create()
                .withIssuer("kinflasy-app")
                .withSubject(user.getUsername())
                .withExpiresAt(generateExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(final String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("kinflasy-app")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.ofHours(-3));
    }

}
