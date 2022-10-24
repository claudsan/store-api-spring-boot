package br.com.claudsan.store.application.events;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

public class StoreEvent extends ApplicationEvent {
    private Serializable message;

    public StoreEvent(Object source, Serializable message) {
        super(source);
        this.message = message;
    }

    public Serializable getMessage() {
        return message;
    }
}