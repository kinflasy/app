package br.org.kinflasy.libs.people.dto.roles;

import java.util.UUID;

import lombok.Data;

@Data
public class RoleDto {

    private UUID id;
    private String name;
    private String slug;

}
