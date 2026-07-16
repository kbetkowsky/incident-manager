package com.betkowski.incidentmanager.adapters.out.messaging;

import com.betkowski.incidentmanager.domain.event.IncidentCreated;
import com.betkowski.incidentmanager.domain.port.IncidentEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class IncidentEventListener {
    private final IncidentEventPublisher incidentEventPublisher;

    public IncidentEventListener(IncidentEventPublisher incidentEventPublisher) {
        this.incidentEventPublisher = incidentEventPublisher;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(IncidentCreated incidentCreated) {
        incidentEventPublisher.publish(incidentCreated);
    }
}
