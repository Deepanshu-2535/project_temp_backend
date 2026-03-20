package com.deepanshu.attendance.services;

import com.deepanshu.attendance.domain.dtos.TeacherDashboardResponse;
import com.deepanshu.attendance.domain.entities.Teacher;

import java.util.List;
import java.util.UUID;

public interface TeacherService {
    String getTeacherIdFromUserId(UUID id);
    Teacher getTeacherFromTeacherId(String teacherId);
    List<TeacherDashboardResponse.SessionHistory> getSessionHistory(String teacherId);
    TeacherDashboardResponse getDashboardResponse(String teacherId);
}
