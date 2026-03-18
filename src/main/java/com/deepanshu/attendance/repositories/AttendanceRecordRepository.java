package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.AttendanceRecord;
import com.deepanshu.attendance.domain.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord,Long> {
    Long countByAttendanceSession_SubjectAndStudent_RollNo(Subject subject, Long rollNo);
    List<AttendanceRecord> findByAttendanceSession_SubjectAndStudent_RollNo(Subject subject,Long rollNo);
}
