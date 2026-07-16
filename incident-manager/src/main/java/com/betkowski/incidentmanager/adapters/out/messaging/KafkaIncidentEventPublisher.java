package com.betkowski.incidentmanager.adapters.out.messaging;

import com.betkowski.incidentmanager.domain.event.IncidentCreated;
import com.betkowski.incidentmanager.domain.port.IncidentEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaIncidentEventPublisher implements IncidentEventPublisher {

    private final KafkaTemplate<String, IncidentCreated> kafkaTemplate;

    @Value("${app.kafka.incidents-topic}")
    private String topic;

    public KafkaIncidentEventPublisher(KafkaTemplate<String, IncidentCreated> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(IncidentCreated incidentCreated) {
        kafkaTemplate.send(topic, incidentCreated.deviceId().toString(), incidentCreated);
    }
}
