package br.org.kinflasy.libs.people.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends PersonRequest {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9][a-z0-9._-]{2,19}$")
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @ToString.Exclude
    private String password;

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Update extends PersonRequest {
        @Email
        @NotBlank
        private String email;
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    public static class UpdateUsername {
        @NotBlank
        @Pattern(regexp = "^[a-z0-9][a-z0-9._-]{2,19}$")
        private String username;
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    public static class UpdatePassword {
        @NotBlank
        @ToString.Exclude
        private String currentPassword;

        @NotBlank
        @ToString.Exclude
        private String newPassword;
    }

}
