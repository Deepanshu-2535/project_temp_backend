package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.AttendanceRecord;
import com.deepanshu.attendance.domain.entities.AttendanceSession;
import com.deepanshu.attendance.domain.entities.Student;
import com.deepanshu.attendance.domain.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord,Long> {
    Long countByAttendanceSession_SubjectAndStudent_RollNo(Subject subject, Long rollNo);
    List<AttendanceRecord> findByAttendanceSession_SubjectAndStudent_RollNo(Subject subject,Long rollNo);
    Long countByAttendanceSession_Subject(Subject subject);
    Long countByAttendanceSession(AttendanceSession attendanceSession);
    Optional<AttendanceRecord> findByAttendanceSessionAndStudent(AttendanceSession attendanceSession, Student student);
}
