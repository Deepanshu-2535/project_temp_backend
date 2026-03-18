package com.deepanshu.attendance.controllers;

import com.deepanshu.attendance.domain.dtos.DetailedAttendanceResponse;
import com.deepanshu.attendance.domain.dtos.StudentOverviewResponse;
import com.deepanshu.attendance.domain.dtos.StudentProfileResponse;
import com.deepanshu.attendance.domain.entities.Student;
import com.deepanshu.attendance.domain.entities.Subject;
import com.deepanshu.attendance.services.StudentService;
import com.deepanshu.attendance.services.SubjectService;
import com.deepanshu.attendance.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/student")
@PreAuthorize("hasRole('STUDENT')")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;
    private final SubjectService subjectService;

    @GetMapping(path = "/attendance/overview")
    public ResponseEntity<StudentOverviewResponse> overViewController(HttpServletRequest request){
        UUID id = userService.getUserIdFromRequest(request);
        Long rollNo = studentService.getStudentRollNoByUserId(id);
        return ResponseEntity.ok(studentService.getOverview(rollNo));
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<StudentProfileResponse> profileController(HttpServletRequest request){
        UUID id = userService.getUserIdFromRequest(request);
        Long rollNo = studentService.getStudentRollNoByUserId(id);
        Student student = studentService.getStudentByRollNo(rollNo);
        StudentProfileResponse response = StudentProfileResponse.builder()
                .rollNo(rollNo)
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .department(student.getDepartment())
                .semester(student.getSemester())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/attendance/subject/{subjectCode}")
    public ResponseEntity<DetailedAttendanceResponse> detailedAttendanceController(@PathVariable String subjectCode, HttpServletRequest request){
        Subject subject = subjectService.getSubjectFromSubjectCode(subjectCode);
        UUID id = userService.getUserIdFromRequest(request);
        Long rollNo = studentService.getStudentRollNoByUserId(id);
        Long totalClasses = subjectService.getTotalClasses(subject);
        Long attended = subjectService.getAttended(subject,rollNo);
        List<DetailedAttendanceResponse.AttendanceHistory> history = subjectService.getAttendanceHistory(subject,rollNo);
        DetailedAttendanceResponse response = DetailedAttendanceResponse.builder()
                .subjectCode(subject.getSubjectCode())
                .subjectName(subject.getSubjectName())
                .totalClasses(totalClasses)
                .attended(attended)
                .history(history)
                .build();
        return ResponseEntity.ok(response);
    }
}

