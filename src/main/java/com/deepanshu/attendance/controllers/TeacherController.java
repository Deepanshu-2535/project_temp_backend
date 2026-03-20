package com.deepanshu.attendance.controllers;

import com.deepanshu.attendance.domain.dtos.TeacherDashboardResponse;
import com.deepanshu.attendance.domain.dtos.TeacherProfileResponse;
import com.deepanshu.attendance.domain.entities.Teacher;
import com.deepanshu.attendance.repositories.UserRepository;
import com.deepanshu.attendance.services.TeacherService;
import com.deepanshu.attendance.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final UserService userService;
    private final TeacherService teacherService;

    @GetMapping(path = "/dashboard")
    public ResponseEntity<TeacherDashboardResponse> dashboardController(HttpServletRequest request){
        UUID id = userService.getUserIdFromRequest(request);

    }


    @GetMapping(path = "/profile")
    public ResponseEntity<TeacherProfileResponse> profileController(HttpServletRequest request) {
        UUID id = userService.getUserIdFromRequest(request);
        String teacherId = teacherService.getTeacherIdFromUserId(id);
        Teacher teacher = teacherService.getTeacherFromTeacherId(teacherId);
        TeacherProfileResponse response = TeacherProfileResponse.builder()
                .teacherId(teacher.getTeacherId())
                .title(teacher.getTitle())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .designation(teacher.getDesignation())
                .department(teacher.getDepartment())
                .build();
        return ResponseEntity.ok(response);
    }
}
