package br.org.kinflasy.libs.churches.dto.departments;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.Extension;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ExtensionSubscriptionDto {

    private UUID id;
    private UUID departmentId;
    private Extension extension;

}
