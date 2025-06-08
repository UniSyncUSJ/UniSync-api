package com.uniSync.uniSync_api.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class EntityNotification extends Notification {

    @Enumerated(EnumType.STRING)
    private EntityType targetType;

    private Long targetId;

    public EntityType getTargetType() {
        return targetType;
    }

    public void setTargetType(EntityType targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }


}

