package br.org.kinflasy.libs.api_utils;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PublisherUtils {

    private final ApplicationEventPublisher publisher;

    public <D> void created(final D dto) {
        publisher.publishEvent(new EntityEvent.Created<>(dto));
    }

    public <D> void updated(final D original, final D modified) {
        publisher.publishEvent(new EntityEvent.Updated<>(original, modified));
    }

    public <D> void deleted(final D dto) {
        publisher.publishEvent(new EntityEvent.Deleted<>(dto));
    }

}
