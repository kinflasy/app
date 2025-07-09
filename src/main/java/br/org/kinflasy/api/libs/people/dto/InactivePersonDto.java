package br.org.kinflasy.api.libs.people.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InactivePersonDto extends PersonDto {

    private String email;

}
