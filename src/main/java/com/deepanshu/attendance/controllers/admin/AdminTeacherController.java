package com.deepanshu.attendance.controllers.admin;

import com.deepanshu.attendance.domain.dtos.TeacherCreationRequest;
import com.deepanshu.attendance.domain.dtos.AdminTeacherDto;
import com.deepanshu.attendance.domain.entities.Teacher;
import com.deepanshu.attendance.domain.entities.User;
import com.deepanshu.attendance.enums.Role;
import com.deepanshu.attendance.services.TeacherService;
import com.deepanshu.attendance.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/admin/teachers")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminTeacherController {

    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<AdminTeacherDto>> getAllTeachers() {
        List<Teacher> teachersList = teacherService.findAll();
        List<AdminTeacherDto> adminTeacherDtos = teachersList.stream()
                .map(AdminTeacherController::getTeacherDto)
                .toList();
        return ResponseEntity.ok(adminTeacherDtos);
    }

    @PostMapping
    public ResponseEntity<AdminTeacherDto> createTeacher(@RequestBody TeacherCreationRequest teacherCreationRequest) {
        String rawPassword = teacherCreationRequest.getFirstName()
                .toLowerCase() + teacherCreationRequest.getTeacherId();
        User user = User.builder()
                .email(teacherCreationRequest.getEmail())
                .passwordHash(passwordEncoder.encode(rawPassword))
                .role(Role.TEACHER)
                .build();
        User savedUser = userService.createUser(user);
        Teacher teacher = Teacher.builder()
                .teacherId(teacherCreationRequest.getTeacherId())
                .user(savedUser)
                .title(teacherCreationRequest.getTitle())
                .firstName(teacherCreationRequest.getFirstName())
                .lastName(teacherCreationRequest.getLastName())
                .designation(teacherCreationRequest.getDesignation())
                .department(teacherCreationRequest.getDepartment())
                .build();
        Teacher savedTeacher = teacherService.save(teacher);
        AdminTeacherDto adminTeacherDto = getTeacherDto(savedTeacher);
        return new ResponseEntity<>(adminTeacherDto, HttpStatus.CREATED);
    }


    @PutMapping(path = "/{teacherId}")
    public ResponseEntity<AdminTeacherDto> updateTeacher(@PathVariable String teacherId, @RequestBody AdminTeacherDto adminTeacherDto) {
        Teacher oldTeacher = teacherService.getTeacherFromTeacherId(teacherId);
        Teacher teacher = Teacher.builder()
                .teacherId(teacherId)
                .user(oldTeacher.getUser())
                .title(adminTeacherDto.getTitle())
                .firstName(adminTeacherDto.getFirstName())
                .lastName(adminTeacherDto.getLastName())
                .designation(adminTeacherDto.getDesignation())
                .department(adminTeacherDto.getDepartment())
                .build();
        Teacher savedTeacher = teacherService.save(teacher);
        AdminTeacherDto responseDto = getTeacherDto(savedTeacher);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping(path = "/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable String teacherId){
        teacherService.delete(teacherId);
        return ResponseEntity.ok(null);
    }

    private static AdminTeacherDto getTeacherDto(Teacher savedTeacher) {
        return AdminTeacherDto.builder()
                .teacherId(savedTeacher.getTeacherId())
                .title(savedTeacher.getTitle())
                .firstName(savedTeacher.getFirstName())
                .lastName(savedTeacher.getLastName())
                .designation(savedTeacher.getDesignation())
                .department(savedTeacher.getDepartment())
                .build();
    }

}
