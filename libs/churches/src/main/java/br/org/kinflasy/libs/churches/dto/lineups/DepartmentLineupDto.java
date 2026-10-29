package br.org.kinflasy.libs.churches.dto.lineups;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DepartmentLineupDto extends LineupDto {

    private UUID departmentId;

}
