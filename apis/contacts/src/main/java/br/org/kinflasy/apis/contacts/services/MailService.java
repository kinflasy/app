package br.org.kinflasy.apis.contacts.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.libs.contacts.dto.MailDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendPlainText(final MailDto dto) {
        sendPlainText(dto.getTo(), dto.getSubject(), dto.getBody());
    }

    public void sendHtml(final MailDto dto) throws MessagingException {
        sendHtml(dto.getTo(), dto.getSubject(), dto.getBody());
    }

    private void sendPlainText(final String to, final String subject, final String body) {
        final var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    private void sendHtml(final String to, final String subject, final String htmlBody) throws MessagingException {
        final var message = mailSender.createMimeMessage();
        final var helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }

}
