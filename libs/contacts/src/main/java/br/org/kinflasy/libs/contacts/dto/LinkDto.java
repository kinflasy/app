package br.org.kinflasy.libs.contacts.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkDto {

    private UUID id;
    private String label;
    private String url;

}
