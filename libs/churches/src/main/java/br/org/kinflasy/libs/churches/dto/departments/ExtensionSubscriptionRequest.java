package br.org.kinflasy.libs.churches.dto.departments;

import br.org.kinflasy.libs.churches.enums.department.Extension;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ExtensionSubscriptionRequest {

    private @NotNull Extension extension;

}
