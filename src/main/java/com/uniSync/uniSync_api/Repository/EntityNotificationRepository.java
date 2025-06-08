package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.EntityNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityNotificationRepository extends JpaRepository<EntityNotification, Long> {
}
