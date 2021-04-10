package com.github.dann41.keycloak;

import com.github.dann41.kafka.EventProducer;
import com.github.dann41.kafka.KafkaProducerFactory;
import com.github.dann41.kafka.KafkaProperties;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ServerInfoAwareProviderFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class KeycloakKafkaEventListenerProviderFactory implements EventListenerProviderFactory, ServerInfoAwareProviderFactory {


    private static final String KAFKA_EVENT_LISTENER = "kafka-event-listener";
    private static final String VERSION = "1.0";

    private KafkaProperties properties;

    @Override
    public void init(Config.Scope scope) {
        System.out.println("Scope " + scope.get("kafka.topic"));
        this.properties = new KafkaProperties(scope);
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new KeycloakKafkaEventListener(new EventProducer(properties, new KafkaProducerFactory(properties)));
    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return KAFKA_EVENT_LISTENER;
    }

    @Override
    public Map<String, String> getOperationalInfo() {
        Map<String, String> ret = new LinkedHashMap<>();
        ret.put("version", VERSION);
        ret.put("host", properties.getHost());
        ret.put("topic", properties.getEventTopic());
        ret.put("admin-topic", properties.getAdminEventTopic());
        return ret;
    }

}
