package com.github.dann41;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventProducer {

    private static final Logger LOG = LoggerFactory.getLogger(EventProducer.class);

    private final KafkaProperties kafkaProperties;
    private final KafkaProducerFactory kafkaProducerFactory;

    public EventProducer(KafkaProperties kafkaProperties, KafkaProducerFactory kafkaProducerFactory) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaProducerFactory = kafkaProducerFactory;
    }

    public void produce(Event event) {
        try {
            String value = new ObjectMapper().writeValueAsString(event);
            produce(kafkaProperties.getEventTopic(), event.getUserId(), value);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            LOG.error("Cannot serialize event", e);
        }
    }

    public void produce(AdminEvent event) {
        try {
            String value = new ObjectMapper().writeValueAsString(event);
            produce(kafkaProperties.getAdminEventTopic(), event.getAuthDetails().getUserId(), value);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            LOG.error("Cannot serialize admin event", e);
        }
    }

    private void produce(String topic, String key, String value) {
        Producer<String, String> producer = kafkaProducerFactory.create();
        try {
            Thread.currentThread().setContextClassLoader(null);
            ProducerRecord<String, String> eventRecord = new ProducerRecord<String, String>(topic, key, value);
            producer.send(eventRecord);
        } finally {
            producer.flush();
            producer.close();
        }
    }
}
