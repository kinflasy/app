package br.org.kinflasy.libs.people.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserIdentifierDto extends PersonIdentifierDto {

    private String username;

}
