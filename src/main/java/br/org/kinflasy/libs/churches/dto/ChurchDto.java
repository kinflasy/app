package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChurchDto {

    private UUID id;
    private String name;
    private String slug;
    private String acronym;
    private String phone;
    private String email;

}
