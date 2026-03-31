package com.deepanshu.attendance.controllers;

import com.deepanshu.attendance.domain.dtos.*;
import com.deepanshu.attendance.domain.entities.AttendanceSession;
import com.deepanshu.attendance.domain.entities.Subject;
import com.deepanshu.attendance.domain.entities.Teacher;
import com.deepanshu.attendance.services.AttendanceSessionService;
import com.deepanshu.attendance.services.TeacherService;
import com.deepanshu.attendance.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/teacher")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class TeacherController {

    private final UserService userService;
    private final TeacherService teacherService;
    private final AttendanceSessionService attendanceSessionService;

    @GetMapping(path = "/dashboard")
    public ResponseEntity<TeacherDashboardResponse> dashboardController(HttpServletRequest request) {
        UUID id = userService.getUserIdFromRequest(request);
        String teacherId = teacherService.getTeacherIdFromUserId(id);
        TeacherDashboardResponse response = teacherService.getDashboardResponse(teacherId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/subjects")
    public ResponseEntity<List<SubjectDto>> subjectsController(HttpServletRequest request) {
        UUID id = userService.getUserIdFromRequest(request);
        String teacherId = teacherService.getTeacherIdFromUserId(id);
        List<Subject> subjects = teacherService.getAllSubjectsFromTeacherId(teacherId);
        List<SubjectDto> response = subjects.stream()
                .map(subject -> SubjectDto.builder()
                        .subjectCode(subject.getSubjectCode())
                        .subjectName(subject.getSubjectName())
                        .build())
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/sessions/{sessionId}/details")
    public ResponseEntity<SessionDetails> sessionDetailsController(@PathVariable Long sessionId) {
        SessionDetails response = attendanceSessionService.getDetailedSessionDTO(sessionId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/sessions/start")
    public ResponseEntity<SessionStartResponse> sessionStartController(HttpServletRequest request, @RequestBody SessionStartRequest sessionStartRequest) {
        AttendanceSession session = attendanceSessionService.createNewSession(sessionStartRequest.getTeacherId(), sessionStartRequest.getSubjectCode());
        SessionStartResponse response = SessionStartResponse.builder()
                .sessionId(session.getId())
                .currentToken(session.getCurrentToken())
                .expiresAt(session.getTokenExpiresAt())
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/sessions/{sessionId}/stop")
    public ResponseEntity<Void> sessionStopController(@PathVariable Long sessionId){
        attendanceSessionService.stopSession(sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/sessions/{sessionId}/token")
    public ResponseEntity<QrTokenResponse> sessionTokenController(@PathVariable Long sessionId){
        AttendanceSession session = attendanceSessionService.getNewToken(sessionId);
        QrTokenResponse response = QrTokenResponse.builder()
                .token(session.getCurrentToken())
                .expiresAt(session.getTokenExpiresAt())
                .build();
        return ResponseEntity.ok(response);
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
