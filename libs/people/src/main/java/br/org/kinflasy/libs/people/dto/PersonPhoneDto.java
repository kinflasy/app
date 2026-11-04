package br.org.kinflasy.libs.people.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PersonPhoneDto {

    private String phone;

}
