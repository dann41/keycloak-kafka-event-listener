package com.github.dann41.kafka;

import com.github.dann41.kafka.EventProducer;
import com.github.dann41.kafka.KafkaProducerFactory;
import com.github.dann41.kafka.KafkaProperties;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.AuthDetails;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventProducerTest
{
    private static final String HOST = "";
    private static final String EVENT_TOPIC = "";
    private static final String ADMIN_EVENT_TOPIC = "";

    @Mock
    private KafkaProducerFactory kafkaProducerFactory;

    private MockProducer<String, String> mockProducer = new MockProducer<>();

    private EventProducer eventProducer;

    @BeforeEach
    public void setup() {
        KafkaProperties kafkaProperties = new KafkaProperties(HOST, EVENT_TOPIC, ADMIN_EVENT_TOPIC);
        when(kafkaProducerFactory.create()).thenReturn(mockProducer);
        eventProducer = new EventProducer(kafkaProperties, kafkaProducerFactory);
    }

    @Test
    public void shouldPublishEvent() {
        // given
        String userId = UUID.randomUUID().toString();
        Event event = new Event();
        event.setUserId(userId);

        // when
        eventProducer.produce(event);

        // then
        assertRecordProduced(userId, EVENT_TOPIC);
    }

    @Test
    public void shouldPublishAdminEvent() {
        // given
        String userId = UUID.randomUUID().toString();
        AdminEvent event = new AdminEvent();
        AuthDetails authDetails = new AuthDetails();
        event.setAuthDetails(authDetails);
        authDetails.setUserId(userId);

        // when
        eventProducer.produce(event);

        // then
        assertRecordProduced(userId, ADMIN_EVENT_TOPIC);
    }

    private void assertRecordProduced(String key, String topic) {
        List<ProducerRecord<String, String>> records = mockProducer.history();
        assertThat(records).hasSize(1);

        ProducerRecord<String, String> record = records.get(0);
        assertThat(record.key()).isEqualTo(key);
        assertThat(record.topic()).isEqualTo(topic);
        assertThat(record.value()).isNotNull();
    }
}
