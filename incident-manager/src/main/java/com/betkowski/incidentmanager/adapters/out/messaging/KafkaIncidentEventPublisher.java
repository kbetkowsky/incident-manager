package com.betkowski.incidentmanager.adapters.out.messaging;

import com.betkowski.incidentmanager.domain.event.IncidentCreated;
import com.betkowski.incidentmanager.domain.port.IncidentEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaIncidentEventPublisher implements IncidentEventPublisher {

    private final KafkaTemplate<String, IncidentCreated> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(KafkaIncidentEventPublisher.class);

    @Value("${app.kafka.incidents-topic}")
    private String topic;

    public KafkaIncidentEventPublisher(KafkaTemplate<String, IncidentCreated> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(IncidentCreated incidentCreated) {
        log.info("Publishing IncidentCreated {} for device {}", incidentCreated.incidentId(), incidentCreated.deviceName());
        kafkaTemplate.send(topic, incidentCreated.deviceId().toString(), incidentCreated);
    }
}
