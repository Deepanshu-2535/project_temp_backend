package com.deepanshu.attendance.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSubjectDto {
    private String subjectCode;
    private String subjectName;
    private String teacherId;
    private Integer semester;
    private String department;
}
