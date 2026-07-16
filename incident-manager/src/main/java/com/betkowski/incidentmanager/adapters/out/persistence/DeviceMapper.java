package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    DeviceEntity toEntity(Device device);
    default Device toDomain(DeviceEntity entity) {
        return Device.restore(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
