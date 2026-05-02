package com.deepanshu.attendance.controllers.admin;

import com.deepanshu.attendance.domain.dtos.AdminEnrollmentDto;
import com.deepanshu.attendance.domain.dtos.AdminStudentDto;
import com.deepanshu.attendance.domain.entities.Enrollment;
import com.deepanshu.attendance.services.EnrollmentService;
import com.deepanshu.attendance.services.StudentService;
import com.deepanshu.attendance.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/admin/enrollments")
@RequiredArgsConstructor
public class AdminEnrollmentsController {
    private final EnrollmentService enrollmentService;
    private final SubjectService subjectService;
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<AdminEnrollmentDto>> getEnrollment() {
        List<Enrollment> enrollments = enrollmentService.findAll();
        List<AdminEnrollmentDto> adminEnrollmentDtos = enrollments.stream()
                .map(AdminEnrollmentsController::getEnrollmentDto)
                .toList();
        return ResponseEntity.ok(adminEnrollmentDtos);
    }

    @PostMapping
    public ResponseEntity<AdminEnrollmentDto> createEnrollment(@RequestBody AdminEnrollmentDto enrollmentDto) {
        Enrollment enrollment = Enrollment.builder()
                .subject(subjectService.getSubjectFromSubjectCode(enrollmentDto.getSubjectCode()))
                .student(studentService.getStudentByRollNo(enrollmentDto.getRollNo()))
                .build();
        Enrollment savedEnrollment = enrollmentService.save(enrollment);
        return ResponseEntity.ok(getEnrollmentDto(savedEnrollment));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AdminEnrollmentDto> updateEnrollment(@PathVariable Long id, @RequestBody AdminEnrollmentDto enrollmentDto) {
        Enrollment oldEnrollment = enrollmentService.getEnrollmentFromId(id); //throws an exception if it does not exist
        Enrollment enrollment = Enrollment.builder()
                .id(id)
                .subject(subjectService.getSubjectFromSubjectCode(enrollmentDto.getSubjectCode()))
                .student(studentService.getStudentByRollNo(enrollmentDto.getRollNo()))
                .build();
        Enrollment savedEnrollment = enrollmentService.save(enrollment);
        return ResponseEntity.ok(getEnrollmentDto(savedEnrollment));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id){
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static AdminEnrollmentDto getEnrollmentDto(Enrollment enrollment) {
        return AdminEnrollmentDto.builder()
                .subjectCode(enrollment.getSubject()
                        .getSubjectCode())
                .rollNo(enrollment.getStudent()
                        .getRollNo())
                .build();
    }
}
