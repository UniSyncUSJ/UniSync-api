package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.StudentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentNotificationRepository extends JpaRepository<StudentNotification, Long> {

    List<StudentNotification> findTop10ByStudentIdAndIsReadFalseOrderByNotification_TimestampDesc(Long studentId);

    List<StudentNotification> findByStudentIdOrderByNotification_TimestampDesc(Long studentId);
}
