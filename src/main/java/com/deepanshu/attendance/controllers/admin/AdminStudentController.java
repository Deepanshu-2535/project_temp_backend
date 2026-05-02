package com.deepanshu.attendance.controllers.admin;

import com.deepanshu.attendance.domain.dtos.StudentCreationRequest;
import com.deepanshu.attendance.domain.dtos.AdminStudentDto;
import com.deepanshu.attendance.domain.entities.Student;
import com.deepanshu.attendance.domain.entities.User;
import com.deepanshu.attendance.enums.Role;
import com.deepanshu.attendance.services.StudentService;
import com.deepanshu.attendance.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/students")
@RequiredArgsConstructor
public class AdminStudentController {
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<AdminStudentDto>> getAllStudents() {
        List<Student> studentList = studentService.findAll();
        List<AdminStudentDto> adminStudentDtoList = studentList.stream()
                .map(AdminStudentController::getStudentDto)
                .toList();
        return ResponseEntity.ok(adminStudentDtoList);
    }

    @PostMapping
    public ResponseEntity<AdminStudentDto> createStudent(@RequestBody StudentCreationRequest studentCreationRequest) {
        String rawPassword = studentCreationRequest.getFirstName() + studentCreationRequest.getRollNo();
        User user = User.builder()
                .email(studentCreationRequest.getEmail())
                .passwordHash(passwordEncoder.encode(rawPassword))
                .role(Role.STUDENT)
                .build();
        User savedUser = userService.createUser(user);
        Student student = Student.builder()
                .rollNo(studentCreationRequest.getRollNo())
                .user(savedUser)
                .firstName(studentCreationRequest.getFirstName())
                .lastName(studentCreationRequest.getLastName())
                .semester(studentCreationRequest.getSemester())
                .department(studentCreationRequest.getDepartment())
                .build();
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(getStudentDto(savedStudent), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{rollNo}")
    public ResponseEntity<AdminStudentDto> updateStudent(@RequestBody AdminStudentDto adminStudentDto, @PathVariable Long rollNo) {
        Student oldStudent = studentService.getStudentByRollNo(rollNo);
        Student newStudent = Student.builder()
                .rollNo(rollNo)
                .user(oldStudent.getUser())
                .firstName(adminStudentDto.getFirstName())
                .lastName(adminStudentDto.getLastName())
                .semester(adminStudentDto.getSemester())
                .department(adminStudentDto.getDepartment())
                .build();
        Student savedStudent = studentService.save(newStudent);
        return ResponseEntity.ok(getStudentDto(savedStudent));
    }

    @DeleteMapping(path = "/{rollNo}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long rollNo){
        studentService.delete(rollNo);
        return ResponseEntity.noContent().build();
    }

    private static AdminStudentDto getStudentDto(Student student) {
        return AdminStudentDto.builder()
                .rollNo(student.getRollNo())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .semester(student.getSemester())
                .department(student.getDepartment())
                .build();
    }

}
