package br.com.claudsan.store.application.events;

import br.com.claudsan.store.adapter.metrics.CustomMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class StoreEventPublisher {

    @Autowired
    private CustomMetrics metrics;

    private static final String METRIC_NAME = "store.events";
    private Logger logger = LoggerFactory.getLogger(StoreEventPublisher.class);
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public void fireEvent(final Serializable event) {
        StoreEvent storeEvent = new StoreEvent(this, event);
        applicationEventPublisher.publishEvent(storeEvent);
        logger.info(String.format("Event fired: %s", event.getClass().getSimpleName()));
        metrics.counter(METRIC_NAME,"type","received", "object", event.getClass().getSimpleName());
    }

}
