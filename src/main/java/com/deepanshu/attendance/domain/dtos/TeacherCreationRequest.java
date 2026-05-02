package com.deepanshu.attendance.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherCreationRequest {
    private String email;
    private String teacherId;
    private String title;
    private String firstName;
    private String lastName;
    private String designation;
    private String department;
}
