package br.org.kinflasy.libs.churches.events.department;

import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import lombok.Data;

public interface ExtensionEvent {

    @Data
    public static class Subscribed implements ExtensionEvent {
        private final ExtensionSubscriptionDto subscription;
    }

}
