package com.deepanshu.attendance.services;

import com.deepanshu.attendance.domain.dtos.DetailedAttendanceResponse;
import com.deepanshu.attendance.domain.entities.Subject;

import java.util.List;

public interface SubjectService {
    Subject getSubjectFromSubjectCode(String subjectCode);
    Long getTotalClassesOfASubject(Subject subject);
    Long getNoOfCLassesOfASubjectAttendedByAStudent(Subject subject, Long rollNo);
    List<DetailedAttendanceResponse.AttendanceHistory> getAttendanceHistory(Subject subject, Long rollNo);
}
