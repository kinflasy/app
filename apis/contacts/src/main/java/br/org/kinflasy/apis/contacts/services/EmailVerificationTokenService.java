package br.org.kinflasy.apis.contacts.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.org.kinflasy.apis.contacts.entities.EmailVerificationToken;
import br.org.kinflasy.apis.contacts.repositories.EmailVerificationTokenRepository;
import br.org.kinflasy.libs.api_utils.enums.TokenType;
import br.org.kinflasy.libs.contacts.dto.EmailVerificationTokenDto;

@Service
public class EmailVerificationTokenService {

    private final ModelMapper mapper;

    private final EmailVerificationTokenRepository repository;

    private final String secret;

    public EmailVerificationTokenService(final ModelMapper mapper, final EmailVerificationTokenRepository repository,
            @Value("${app.api.security.token.secret}") final String secret) {
        this.mapper = mapper;
        this.repository = repository;
        this.secret = secret;
    }

    public EmailVerificationTokenDto create(final UUID emailId) {
        // Buscar token preexistente para sobrescrever
        final var entity = repository.findByEmailId(emailId)

                // Ou criar um novo token caso não exista
                .orElseGet(EmailVerificationToken::new);

        // Preencher os campos do token
        entity.setEmailId(emailId);
        entity.setToken(generateToken(emailId));

        // Persistir na tabela
        final var saved = repository.save(entity);
        return mapper.map(saved, EmailVerificationTokenDto.class);
    }

    public EmailVerificationTokenDto validate(final String token) {
        final var subject = validateToken(token);

        return repository.findByToken(token)
                .map(entity -> {
                    final var isConfirmed = entity.getEmailId().toString().equals(subject);

                    if (isConfirmed) {
                        repository.delete(entity);
                    }

                    return mapper.map(entity, EmailVerificationTokenDto.class)
                            .setVerified(isConfirmed);
                })
                .orElseGet(() -> new EmailVerificationTokenDto()
                        .setToken(token)
                        .setVerified(false));
    }

    private String generateToken(final UUID emailId) {
        return JWT.create()
                .withIssuer("kinflasy-app")
                .withClaim("type", TokenType.EMAIL_VERIFICATION_TOKEN.name())
                .withSubject(emailId.toString())
                .withExpiresAt(generateExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }

    private String validateToken(final String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("kinflasy-app")
                .withClaim("type", TokenType.EMAIL_VERIFICATION_TOKEN.name())
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.ofHours(-3));
    }

}
