package br.com.claudsan.store.adapter.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    @Autowired
    private MeterRegistry registry;

    public void counter(String name, String... tags) {
        Counter.builder(name)
                .tags(tags)
                .tag("application","store-api")
                .register(registry)
                .increment();
    }
}
