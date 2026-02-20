package br.org.kinflasy.libs.churches.events.department;

import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;

public abstract class ExtensionEvent<D> extends EntityEvent<D> {

    protected ExtensionEvent(final D source) {
        super(source);
    }

    public static class Subscribed extends ExtensionEvent<ExtensionSubscriptionDto> {
        public Subscribed(final ExtensionSubscriptionDto source) {
            super(source);
        }
    }

}
