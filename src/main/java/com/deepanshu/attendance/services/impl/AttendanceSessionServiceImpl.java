package com.deepanshu.attendance.services.impl;

import com.deepanshu.attendance.domain.dtos.SessionDetails;
import com.deepanshu.attendance.domain.entities.AttendanceRecord;
import com.deepanshu.attendance.domain.entities.AttendanceSession;
import com.deepanshu.attendance.domain.entities.Student;
import com.deepanshu.attendance.enums.Status;
import com.deepanshu.attendance.exceptions.InvalidSessionException;
import com.deepanshu.attendance.exceptions.ResourceNotFoundException;
import com.deepanshu.attendance.exceptions.StudentNotEnrolledException;
import com.deepanshu.attendance.repositories.AttendanceRecordRepository;
import com.deepanshu.attendance.repositories.AttendanceSessionRepository;
import com.deepanshu.attendance.repositories.EnrollmentRepository;
import com.deepanshu.attendance.services.AttendanceSessionService;
import com.deepanshu.attendance.services.StudentService;
import com.deepanshu.attendance.services.SubjectService;
import com.deepanshu.attendance.services.TeacherService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceSessionServiceImpl implements AttendanceSessionService {

    @Value("${jwt.qr.secret}")
    private String secretKey;

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AttendanceSessionRepository attendanceSessionRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;


    @Override
    public AttendanceSession getSessionFromSessionId(Long sessionId) {
        return attendanceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
    }

    @Override
    public SessionDetails getDetailedSessionDTO(Long sessionId) {
        AttendanceSession session = getSessionFromSessionId(sessionId);
        List<Student> listOfStudentsInTheSubject = enrollmentRepository.findBySubject(session.getSubject());
        List<SessionDetails.Attendance> attendances = listOfStudentsInTheSubject.stream()
                .map(student -> {
                    Optional<AttendanceRecord> attendanceRecord = attendanceRecordRepository.findByAttendanceSessionAndStudent(session, student);
                    Status attendanceStatus = attendanceRecord.isEmpty() ? Status.ABSENT : Status.PRESENT;
                    return SessionDetails.Attendance.builder()
                            .rollNo(student.getRollNo())
                            .studentName(student.getFirstName() + " " + student.getLastName())
                            .attendanceStatus(attendanceStatus)
                            .build();
                })
                .toList();

        return SessionDetails.builder()
                .subjectCode(session.getSubject()
                        .getSubjectCode())
                .subjectName(session.getSubject()
                        .getSubjectName())
                .sessionDate(session.getSessionDate())
                .totalNoOfStudents(enrollmentRepository.countBySubject(session.getSubject()))
                .noOfStudentsPresent(attendanceRecordRepository.countByAttendanceSession(session))
                .attendances(attendances)
                .build();
    }

    @Override
    public AttendanceSession createNewSession(String teacherId, String subjectCode) {
        Optional<AttendanceSession> activeSession = attendanceSessionRepository.findBySubject_SubjectCodeAndIsActiveTrue(subjectCode);
        if (activeSession.isPresent()) {
            throw new IllegalStateException("A session is already active for this subject");
        }
        AttendanceSession newSession = AttendanceSession.builder()
                .subject(subjectService.getSubjectFromSubjectCode(subjectCode))
                .teacher(teacherService.getTeacherFromTeacherId(teacherId))
                .sessionDate(LocalDate.now())
                .isActive(true)
                .build();
        attendanceSessionRepository.save(newSession);
        String qrToken = generateToken(newSession.getId());
        newSession.setCurrentToken(qrToken);
        newSession.setTokenExpiresAt(LocalDateTime.now()
                .plusSeconds(20));
        return attendanceSessionRepository.save(newSession);
    }

    @Override
    public void stopSession(Long sessionId) {
        AttendanceSession session = getSessionFromSessionId(sessionId);
        session.setIsActive(false);
        session.setCurrentToken(null);
        session.setTokenExpiresAt(null);
        attendanceSessionRepository.save(session);
    }

    @Override
    public AttendanceSession getNewToken(Long sessionId) {
        String token = generateToken(sessionId);
        AttendanceSession session = getSessionFromSessionId(sessionId);
        session.setCurrentToken(token);
        session.setTokenExpiresAt(LocalDateTime.now()
                .plusSeconds(100));
        attendanceSessionRepository.save(session);
        return getSessionFromSessionId(sessionId);
    }

    @Override
    public AttendanceRecord markAttendance(String token, Long rollNo) {
        AttendanceSession session = getSessionFromToken(token);
        Student student = studentService.getStudentByRollNo(rollNo);
        if (!(session.getIsActive() && session.getTokenExpiresAt()
                .isAfter(LocalDateTime.now()))) {
            throw new InvalidSessionException("Session is Expired or invalid");
        }
        if (enrollmentRepository.findBySubjectAndStudent_RollNo(session.getSubject(), rollNo)
                .isEmpty()) {
            throw new StudentNotEnrolledException("Student not enrolled");
        }
        Optional<AttendanceRecord> existing = attendanceRecordRepository.findByAttendanceSessionAndStudent(session, student);
        if (existing.isPresent()) {
            return existing.get();
        }
        AttendanceRecord newRecord = AttendanceRecord.builder()
                .attendanceSession(session)
                .student(student)
                .scannedAt(LocalDateTime.now())
                .build();
        return attendanceRecordRepository.save(newRecord);
    }

    private AttendanceSession getSessionFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new InvalidSessionException("Session is expired");
        } catch (SignatureException e){
            throw new InvalidSessionException("Invalid Session");
        }
        Long sessionId = Long.parseLong(claims.getSubject());
        return attendanceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
    }

    private String generateToken(Long sessionId) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(sessionId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 20000))
                .signWith(getSigningKey())
                .compact();

    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
