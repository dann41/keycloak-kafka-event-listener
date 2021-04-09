package com.github.dann41;

public class KafkaProperties {

    private final String host;
    private final String eventTopic;
    private final String adminEventTopic;

    KafkaProperties(String host, String eventTopic, String adminEventTopic) {
        validateNotNull(host, "host cannot be null");
        validateNotNull(eventTopic, "eventTopic cannot be null");
        validateNotNull(adminEventTopic, "adminEventTopic cannot be null");

        this.host = host;
        this.eventTopic = eventTopic;
        this.adminEventTopic = adminEventTopic;
    }

    String getHost() {
        return host;
    }

    String getEventTopic() {
        return eventTopic;
    }

    String getAdminEventTopic() {
        return adminEventTopic;
    }

    private void validateNotNull(String property, String errorMessage) {
        if (property == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
