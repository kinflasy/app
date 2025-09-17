package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChurchDto {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public class Starter extends ChurchDto {
        private UnitDto unit;
    }

    private UUID id;
    private String name;
    private String slug;
    private String acronym;
    private String phone;
    private String email;

}
