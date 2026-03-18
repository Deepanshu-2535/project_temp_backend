package com.deepanshu.attendance.domain.dtos;

import com.deepanshu.attendance.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedAttendanceResponse {
    String subjectCode;
    String subjectName;
    Long totalClasses;
    Long attended;
    List<AttendanceHistory> history;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AttendanceHistory{
        LocalDate date;
        Status status;
    }
}
