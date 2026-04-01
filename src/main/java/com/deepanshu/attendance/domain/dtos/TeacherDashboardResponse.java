package com.deepanshu.attendance.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDashboardResponse {
    String title;
    String firstName;
    String lastName;
    Long noOfSubjects;
    Long totalStudents;
    Long averageAttendancePercentage;
    List<SessionHistory> sessionHistory;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SessionHistory{
        Long sessionId;
        String subjectCode;
        String subjectName;
        LocalDate sessionDate;
        Long noOfStudentsPresent;
        Long totalNoOfStudents;
    }
}

