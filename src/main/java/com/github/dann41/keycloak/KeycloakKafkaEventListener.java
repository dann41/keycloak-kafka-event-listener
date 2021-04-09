package com.github.dann41.keycloak;

import com.github.dann41.kafka.EventProducer;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class KeycloakKafkaEventListener implements EventListenerProvider {

    private final EventProducer eventProducer;

    public KeycloakKafkaEventListener(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @Override
    public void onEvent(Event event) {
        eventProducer.produce(event);
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        eventProducer.produce(adminEvent);
    }

    @Override
    public void close() {

    }
}
