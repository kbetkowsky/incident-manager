package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeviceRepositoryAdapter implements DeviceRepository {
    private final DeviceJpaRepository jpaRepository;
    private final DeviceMapper mapper;

    @Override
    public Optional<Device> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void save(Device device) {
        DeviceEntity entity = mapper.toEntity(device);
        jpaRepository.save(entity);
    }
}
