package br.org.kinflasy.libs.churches.dto.departments;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnitLineupDto extends LineupDto {

    private UUID unitId;

}
