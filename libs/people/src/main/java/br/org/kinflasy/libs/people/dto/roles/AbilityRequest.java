package br.org.kinflasy.libs.people.dto.roles;

import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AbilityRequest {

    private UUID roleId;

}
