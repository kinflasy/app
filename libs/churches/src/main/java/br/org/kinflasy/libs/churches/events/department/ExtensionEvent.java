package br.org.kinflasy.libs.churches.events.department;

import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.Data;

public interface ExtensionEvent<D> extends EntityEvent<D> {

    @Data
    public static class Subscribed implements ExtensionEvent<ExtensionSubscriptionDto> {
        private final ExtensionSubscriptionDto dto;
    }

}
