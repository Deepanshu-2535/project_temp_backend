package com.deepanshu.attendance.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentProfileResponse {
    String firstName;
    String lastName;
    Integer semester;
    Long rollNo;
    String department;
}
