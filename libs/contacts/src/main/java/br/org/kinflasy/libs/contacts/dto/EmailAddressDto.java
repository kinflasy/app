package br.org.kinflasy.libs.contacts.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class EmailAddressDto {

    private UUID id;
    private String address;
    private LocalDateTime verifiedAt;

}
