package br.org.kinflasy.libs.contacts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class MailDto {

    private String to;
    private String subject;
    private String body;

}
