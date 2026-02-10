package br.org.kinflasy.libs.churches.events.department;

import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ExtensionEvent {

    @Data
    public static class Subscribed {
        private final ExtensionSubscriptionDto subscription;
    }

}
