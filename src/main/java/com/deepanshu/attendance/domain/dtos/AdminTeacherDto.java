package com.deepanshu.attendance.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTeacherDto {
    private String teacherId;
    private String title;
    private String firstName;
    private String lastName;
    private String designation;
    private String department;
}
