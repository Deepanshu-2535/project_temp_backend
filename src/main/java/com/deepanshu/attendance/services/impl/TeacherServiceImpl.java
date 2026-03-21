package com.deepanshu.attendance.services.impl;

import com.deepanshu.attendance.domain.dtos.TeacherDashboardResponse;
import com.deepanshu.attendance.domain.entities.AttendanceSession;
import com.deepanshu.attendance.domain.entities.Subject;
import com.deepanshu.attendance.domain.entities.Teacher;
import com.deepanshu.attendance.exceptions.ResourceNotFoundException;
import com.deepanshu.attendance.repositories.*;
import com.deepanshu.attendance.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final AttendanceSessionRepository attendanceSessionRepository;

    @Override
    public String getTeacherIdFromUserId(UUID id) {
        Optional<Teacher> teacher = teacherRepository.findByUser_Id(id);
        return teacher.orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found"))
                .getTeacherId();
    }

    @Override
    public Teacher getTeacherFromTeacherId(String teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found"));
    }

    @Override
    public TeacherDashboardResponse getDashboardResponse(String teacherId) {
        Long noOfSubjects = subjectRepository.countByTeacher_TeacherId(teacherId);
        List<Subject> listOfSubjects = subjectRepository.findByTeacher_TeacherId(teacherId);
        Long totalNoOfStudents = enrollmentRepository.countDistinctStudentsByTeacherId(teacherId);
        long totalSessions = listOfSubjects.stream()
                .mapToLong(subject->{
                    Long noOfStudentsInASubject = enrollmentRepository.countBySubject(subject);
                    Long sessionsOfASubject = attendanceSessionRepository.countBySubject(subject);
                    return noOfStudentsInASubject*sessionsOfASubject;
                })
                .sum();
        long attendedSessions = listOfSubjects.stream()
                .mapToLong(attendanceRecordRepository::countByAttendanceSession_Subject)
                .sum();
        Long attendancePercentage = totalSessions==0?0L: ((attendedSessions*100) / totalSessions);
        List<TeacherDashboardResponse.SessionHistory> sessionHistories = getSessionHistories(teacherId);
        return TeacherDashboardResponse.builder()
                .noOfSubjects(noOfSubjects)
                .totalStudents(totalNoOfStudents)
                .averageAttendancePercentage(attendancePercentage)
                .sessionHistory(sessionHistories)
                .build();
    }



    private List<TeacherDashboardResponse.SessionHistory> getSessionHistories(String teacherId) {
        List<AttendanceSession> sessionsByTeacher = attendanceSessionRepository.findByTeacher_TeacherId(teacherId);
        return sessionsByTeacher.stream()
                .map(session -> TeacherDashboardResponse.SessionHistory.builder()
                        .sessionId(session.getId())
                        .subjectCode(session.getSubject()
                                .getSubjectCode())
                        .subjectName(session.getSubject()
                                .getSubjectName())
                        .sessionDate(session.getSessionDate())
                        .noOfStudentsPresent(attendanceRecordRepository.countByAttendanceSession(session))
                        .totalNoOfStudents(enrollmentRepository.countBySubject(session.getSubject()))
                        .build())
                .toList();
    }

    @Override
    public List<Subject> getAllSubjectsFromTeacherId(String teacherId) {
        return subjectRepository.findByTeacher_TeacherId(teacherId);
    }
}
