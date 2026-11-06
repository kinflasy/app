package br.org.kinflasy.apis.contacts.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.contacts.services.EmailAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/contacts/mail")
@Tag(name = "Email")
@AllArgsConstructor
public class EmailVerificationTokenController {

    private final EmailAddressService emailAddressService;

    @GetMapping("send-verification/{emailId}")
    public ResponseEntity<Void> sendToken(@PathVariable final UUID emailId) {
        emailAddressService.sendVerificationEmail(emailId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("send-verification")
    public ResponseEntity<Void> sendToken() {
        emailAddressService.sendVerificationEmail();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("verify/{token}")
    public ResponseEntity<Void> validateToken(@PathVariable final String token) {
        final var result = emailAddressService.verifyEmail(token);
        return result.isVerified() ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

}
