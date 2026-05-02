package com.deepanshu.attendance.controllers.admin;

import com.deepanshu.attendance.domain.dtos.AdminSubjectDto;
import com.deepanshu.attendance.domain.entities.Subject;
import com.deepanshu.attendance.services.SubjectService;
import com.deepanshu.attendance.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/admin/subjects")
@RequiredArgsConstructor
public class AdminSubjectController {
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<AdminSubjectDto>> getAllSubjects() {
        List<Subject> subjectList = subjectService.findAll();
        List<AdminSubjectDto> subjectDtoList = subjectList.stream()
                .map(AdminSubjectController::getSubjectDto)
                .toList();
        return ResponseEntity.ok(subjectDtoList);
    }

    @PostMapping
    public ResponseEntity<AdminSubjectDto> createSubject(@RequestBody AdminSubjectDto subjectDto) {
        Subject subject = Subject.builder()
                .subjectCode(subjectDto.getSubjectCode())
                .subjectName(subjectDto.getSubjectName())
                .teacher(teacherService.getTeacherFromTeacherId(subjectDto.getTeacherId()))
                .semester(subjectDto.getSemester())
                .department(subjectDto.getDepartment())
                .build();
        Subject savedSubject = subjectService.save(subject);
        return ResponseEntity.ok(getSubjectDto(savedSubject));
    }

    @PutMapping(path = "/{subjectCode}")
    public ResponseEntity<AdminSubjectDto> updateSubject(@RequestBody AdminSubjectDto subjectDto, @PathVariable String subjectCode) {
        Subject oldSubject = subjectService.getSubjectFromSubjectCode(subjectCode); //throws an exception if not found
        Subject subject = Subject.builder()
                .subjectCode(oldSubject.getSubjectCode())
                .subjectName(subjectDto.getSubjectName())
                .teacher(teacherService.getTeacherFromTeacherId(subjectDto.getTeacherId()))
                .semester(subjectDto.getSemester())
                .department(subjectDto.getDepartment())
                .build();
        Subject savedSubject = subjectService.save(subject);
        return ResponseEntity.ok(getSubjectDto(savedSubject));
    }

    @DeleteMapping(path = "/{subjectCode}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String subjectCode){
        subjectService.delete(subjectCode);
        return ResponseEntity.noContent().build();
    }

    private static AdminSubjectDto getSubjectDto(Subject subject) {
        return AdminSubjectDto.builder()
                .subjectCode(subject.getSubjectCode())
                .subjectName(subject.getSubjectName())
                .teacherId(subject.getTeacher()
                        .getTeacherId())
                .semester(subject.getSemester())
                .department(subject.getDepartment())
                .build();
    }
}
