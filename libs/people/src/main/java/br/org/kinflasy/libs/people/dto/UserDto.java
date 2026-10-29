package br.org.kinflasy.libs.people.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends PersonDto {

    private String username;
    private String email;
    private LocalDateTime emailVerifiedAt;

}
