package com.betkowski.incidentmanager.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventJpaRepository extends JpaRepository<EventEntity, UUID> {
}
