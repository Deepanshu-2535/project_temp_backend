package com.deepanshu.attendance.services.impl;

import com.deepanshu.attendance.domain.dtos.StudentOverviewResponse;
import com.deepanshu.attendance.domain.entities.Student;
import com.deepanshu.attendance.domain.entities.Subject;
import com.deepanshu.attendance.exceptions.ResourceNotFoundException;
import com.deepanshu.attendance.repositories.EnrollmentRepository;
import com.deepanshu.attendance.repositories.StudentRepository;
import com.deepanshu.attendance.services.StudentService;
import com.deepanshu.attendance.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SubjectService subjectService;

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void delete(Long rollNo) {
        studentRepository.deleteById(rollNo);
    }

    @Override
    public Long getStudentRollNoByUserId(UUID id) {
        Optional<Student> student = studentRepository.findByUser_Id(id);
        return student.orElseThrow(()->new ResourceNotFoundException("Student Not Found")).getRollNo();
    }

    @Override
    public Student getStudentByRollNo(Long rollNo) {
        return studentRepository.findById(rollNo).orElseThrow(()->new ResourceNotFoundException("Student not found"));
    }

    private List<StudentOverviewResponse.SubjectAttendance> getSubjectWiseAttendance(Long rollNo) {
        List<Subject> enrolledSubjects = enrollmentRepository.findSubjectByStudent_RollNo(rollNo);
        return enrolledSubjects.stream()
                .map(subject -> {
                    Long totalAttendance = subjectService.getTotalClassesOfASubject(subject);
                    Long attended = subjectService.getNoOfCLassesOfASubjectAttendedByAStudent(subject,rollNo);
                    return StudentOverviewResponse.SubjectAttendance.builder()
                            .subjectCode(subject.getSubjectCode())
                            .subjectName(subject.getSubjectName())
                            .totalClasses(totalAttendance)
                            .attended(attended)
                            .build();
                } ).toList();
    }

    public StudentOverviewResponse getOverview(Long rollNo){
        Student student = getStudentByRollNo(rollNo);
        List<StudentOverviewResponse.SubjectAttendance> subjectAttendanceList = getSubjectWiseAttendance(rollNo);
        Long totalClasses = subjectAttendanceList.stream()
                .mapToLong(StudentOverviewResponse.SubjectAttendance::getTotalClasses)
                .sum();
        Long attended = subjectAttendanceList.stream()
                .mapToLong(StudentOverviewResponse.SubjectAttendance::getAttended)
                .sum();
        return StudentOverviewResponse.builder()
                .rollNo(student.getRollNo())
                .firstName(student.getFirstName())
                .semester(student.getSemester())
                .totalClasses(totalClasses)
                .attended(attended)
                .subjectWiseAttendance(subjectAttendanceList)
                .build();
    }
}
