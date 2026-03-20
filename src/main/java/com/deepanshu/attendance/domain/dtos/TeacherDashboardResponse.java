package com.deepanshu.attendance.domain.dtos;

import java.util.List;

public class TeacherDashboardResponse {
    Long noOfSubjects;
    Long totalStudents;
    Long averageAttendancePercentage;
    List<SessionHistory> sessionHistory;

    public static class SessionHistory{
        String subjectCode;
        String subjectName;
        String sessionDate;
        Long noOfStudentsPresent;
        Long totalNoOfStudents;
    }
}

