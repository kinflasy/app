package br.org.kinflasy.libs.people.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InactivePersonDto extends PersonDto {

    private UUID churchId;
    private String email;

}
