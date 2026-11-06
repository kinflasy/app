package br.org.kinflasy.apis.contacts.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.contacts.entities.EmailAddress;
import br.org.kinflasy.apis.contacts.repositories.EmailAddressRepository;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.contacts.dto.EmailAddressDto;
import br.org.kinflasy.libs.contacts.dto.EmailAddressRequest;
import br.org.kinflasy.libs.contacts.dto.EmailVerificationTokenDto;
import br.org.kinflasy.libs.contacts.dto.MailDto;
import jakarta.mail.MessagingException;

@Service
public class EmailAddressService {

    private final ModelMapper mapper;
    private final AuthUtils authUtils;

    private final EmailAddressRepository emailAddressRepository;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final MailService mailService;

    private final String baseUrl;

    // Todo atualizar FGA

    public EmailAddressService(final ModelMapper mapper, final AuthUtils authUtils,
            final EmailAddressRepository emailAddressRepository,
            final EmailVerificationTokenService emailVerificationTokenService, final MailService mailService,
            @Value("${app.base-url}") final String baseUrl) {
        this.mapper = mapper;
        this.authUtils = authUtils;
        this.emailAddressRepository = emailAddressRepository;
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.mailService = mailService;
        this.baseUrl = baseUrl;
    }

    public EmailAddressDto create(final EmailAddressRequest request) {
        final var entity = new EmailAddress();
        entity.setAddress(request.getAddress());

        final var saved = emailAddressRepository.save(entity);

        return mapper.map(saved, EmailAddressDto.class);
    }

    public Optional<EmailAddressDto> findById(final UUID id) {
        return emailAddressRepository.findById(id)
                .map(entity -> mapper.map(entity, EmailAddressDto.class));
    }

    public List<EmailAddressDto> findByAddress(final String address) {
        return emailAddressRepository.findByAddress(address).stream()
                .map(entity -> mapper.map(entity, EmailAddressDto.class))
                .toList();
    }

    public void delete(final UUID id) {
        emailAddressRepository.deleteById(id);
    }

    public void sendVerificationEmail(final UUID id) {
        findById(id)
                .ifPresent(emailDto -> {
                    if (emailDto.getVerifiedAt() != null) {
                        throw new IllegalStateException("O endereço de e-mail já foi verificado");
                    }

                    final var tokenDto = emailVerificationTokenService.create(id);
                    final var url = "%s/v1/core/contacts/mail/verify/%s"
                            .formatted(baseUrl, tokenDto.getToken());

                    final var mail = new MailDto()
                            .setTo(emailDto.getAddress())
                            .setSubject("Pontis - Confirme seu endereço de e-mail")
                            .setBody("""
                                    <h1>Confirme seu endereço de e-mail</h1>
                                    <p>Por favor, clique no link abaixo para confirmar seu endereço de e-mail:</p>
                                    <a href="%s">Confirmar e-mail</a>
                                    """.formatted(url));
                    try {
                        mailService.sendHtml(mail);
                    } catch (final MessagingException e) {
                        throw new IllegalStateException("Falha ao enviar e-mail de verificação", e);
                    }
                });
    }

    @PreAuthorize("isAuthenticated()")
    public void sendVerificationEmail() {
        final var loggedUser = authUtils.getLoggedUser();
        final var emailId = loggedUser.getEmail().getId();

        sendVerificationEmail(emailId);
    }

    public EmailVerificationTokenDto verifyEmail(final String token) {
        final var result = emailVerificationTokenService.validate(token);

        if (result.isVerified()) {
            emailAddressRepository.findById(result.getEmailId())
                    .ifPresent(emailData -> {
                        emailData.setVerifiedAt(LocalDateTime.now());
                        emailAddressRepository.save(emailData);
                    });
        }

        return result;
    }

}
