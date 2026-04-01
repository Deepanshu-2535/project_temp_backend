package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.AttendanceSession;
import com.deepanshu.attendance.domain.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession,Long> {
    Long countBySubject(Subject subject);
    List<AttendanceSession> findBySubject_SubjectCodeOrderBySessionDateDesc(String subjectCode);
    List<AttendanceSession> findByTeacher_TeacherId(String teacherId);
    Optional<AttendanceSession> findBySubject_SubjectCodeAndIsActiveTrue(String subjectCode);
}
