package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.AttendanceSession;
import com.deepanshu.attendance.domain.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession,Long> {
    Long countBySubject(Subject subject);
    List<AttendanceSession> findBySubject(Subject subject);
}
