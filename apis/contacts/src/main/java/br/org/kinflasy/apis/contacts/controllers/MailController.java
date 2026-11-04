package br.org.kinflasy.apis.contacts.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.contacts.services.MailService;
import br.org.kinflasy.libs.contacts.dto.MailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/contacts/mail/send")
@Tag(name = "Email")
@AllArgsConstructor
public class MailController {

    private final MailService service;

    @PostMapping
    @Operation(summary = "SISTEMA - Enviar email", description = "Enviar um email de texto simples.")
    public void sendPlainTextEmail(@RequestBody final MailDto dto) {
        service.sendPlainText(dto);
    }

    @PostMapping("html")
    @Operation(summary = "SISTEMA - Enviar email", description = "Enviar um email de texto HTML.")
    public void sendHtmlEmail(@RequestBody final MailDto dto) throws MessagingException {
        service.sendHtml(dto);
    }

}
