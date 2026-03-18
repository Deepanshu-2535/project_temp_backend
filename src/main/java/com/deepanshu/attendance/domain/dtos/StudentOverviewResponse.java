package com.deepanshu.attendance.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentOverviewResponse {
    String firstName;
    Long rollNo;
    Integer semester;
    Long totalClasses;
    Long attended;
    List<SubjectAttendance> subjectWiseAttendance;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubjectAttendance {
        String subjectCode;
        String subjectName;
        Long totalClasses;
        Long attended;
    }
}
