package com.github.dann41.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerFactory {

    private final KafkaProperties kafkaProperties;


    public KafkaProducerFactory(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public Producer<String, String> create() {
        return new KafkaProducer<>(getProperties());
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "keycloak-kafka-event-listener");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getHost());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
