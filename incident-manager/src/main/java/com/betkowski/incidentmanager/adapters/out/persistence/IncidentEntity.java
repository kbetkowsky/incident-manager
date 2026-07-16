package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.model.IncidentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "incidents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class IncidentEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID deviceId;

    @Column(nullable = false)
    private String deviceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status;

    @Column(nullable = false)
    private int occurrenceCount;

    @Column(nullable = false)
    private Instant firstOccurredAt;

    @Column(nullable = false)
    private Instant lastOccurredAt;

}
