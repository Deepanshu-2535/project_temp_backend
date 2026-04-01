package com.deepanshu.attendance.domain.dtos;

import com.deepanshu.attendance.enums.Status;
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
public class SessionDetails {
    String subjectCode;
    String subjectName;
    LocalDate sessionDate;
    Long totalNoOfStudents;
    Long noOfStudentsPresent;
    List<Attendance> attendances;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Attendance {
        Long rollNo;
        String studentName;
        Status attendanceStatus;
    }
}
