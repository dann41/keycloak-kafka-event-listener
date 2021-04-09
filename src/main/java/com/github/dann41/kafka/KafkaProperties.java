package com.github.dann41.kafka;

import org.keycloak.Config;

public class KafkaProperties {

    private final String host;
    private final String eventTopic;
    private final String adminEventTopic;

    public KafkaProperties(Config.Scope scope) {
        this(
                scope.get("kafka.host", "kafka:9092"),
                scope.get("kafka.topic", "keycloak-event"),
                scope.get("kafka.admin_topic", "keycloak-event")
        );
    }

    public KafkaProperties(String host, String eventTopic, String adminEventTopic) {
        validateNotNull(host, "host cannot be null");
        validateNotNull(eventTopic, "eventTopic cannot be null");
        validateNotNull(adminEventTopic, "adminEventTopic cannot be null");

        this.host = host;
        this.eventTopic = eventTopic;
        this.adminEventTopic = adminEventTopic;
    }

    public String getHost() {
        return host;
    }

    public String getEventTopic() {
        return eventTopic;
    }

    public String getAdminEventTopic() {
        return adminEventTopic;
    }

    private void validateNotNull(String property, String errorMessage) {
        if (property == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
