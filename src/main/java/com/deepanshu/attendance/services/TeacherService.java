package com.deepanshu.attendance.services;

import com.deepanshu.attendance.domain.dtos.TeacherDashboardResponse;
import com.deepanshu.attendance.domain.entities.Subject;
import com.deepanshu.attendance.domain.entities.Teacher;

import java.util.List;
import java.util.UUID;

public interface TeacherService {
    Teacher save(Teacher teacher);
    List<Teacher> findAll();
    void delete(String teacherId);
    String getTeacherIdFromUserId(UUID id);
    Teacher getTeacherFromTeacherId(String teacherId);
    TeacherDashboardResponse getDashboardResponse(String teacherId);
    List<Subject> getAllSubjectsFromTeacherId(String teacherId);
}
