package br.org.kinflasy.libs.contacts.dto;

import lombok.Data;

@Data
public class MailDto {

    private String to;
    private String subject;
    private String body;

}
