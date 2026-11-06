package br.org.kinflasy.libs.contacts.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class EmailVerificationTokenDto {

    private UUID id;
    private UUID emailId;
    private String token;
    private boolean verified;

}
