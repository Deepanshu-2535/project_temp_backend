package com.deepanshu.attendance.services;

import com.deepanshu.attendance.domain.dtos.StudentOverviewResponse;
import com.deepanshu.attendance.domain.entities.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    Student save(Student student);
    List<Student> findAll();
    void delete(Long rollNo);
    Long getStudentRollNoByUserId(UUID id);
    Student getStudentByRollNo(Long rollNo);
    StudentOverviewResponse getOverview(Long rollNo);

}
