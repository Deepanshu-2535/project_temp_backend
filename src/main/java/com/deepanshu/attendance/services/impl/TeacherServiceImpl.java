package com.deepanshu.attendance.services.impl;

import com.deepanshu.attendance.domain.dtos.TeacherDashboardResponse;
import com.deepanshu.attendance.domain.entities.Subject;
import com.deepanshu.attendance.domain.entities.Teacher;
import com.deepanshu.attendance.exceptions.ResourceNotFoundException;
import com.deepanshu.attendance.repositories.SubjectRepository;
import com.deepanshu.attendance.repositories.TeacherRepository;
import com.deepanshu.attendance.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public String getTeacherIdFromUserId(UUID id) {
        Optional<Teacher> teacher = teacherRepository.findByUser_Id(id);
        return  teacher.orElseThrow(()->new ResourceNotFoundException("Teacher Not Found")).getTeacherId();
    }

    @Override
    public Teacher getTeacherFromTeacherId(String teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(()-> new ResourceNotFoundException("Teacher Not Found"));
    }

    @Override
    public List<TeacherDashboardResponse.SessionHistory> getSessionHistory(String teacherId) {
        return List.of();
    }

    @Override
    public TeacherDashboardResponse getDashboardResponse(String teacherId) {
        Long noOfSubjects = subjectRepository.countByTeacher_TeacherId(teacherId);
        List<Subject> listOfSubjects = subjectRepository.findByTeacher_TeacherId(teacherId);
        Long totalNoOfStudents = listOfSubjects.stream()
                .mapToLong(subject->)
    }
}
