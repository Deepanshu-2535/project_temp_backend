package com.deepanshu.attendance.services.impl;

import com.deepanshu.attendance.domain.dtos.DetailedAttendanceResponse;
import com.deepanshu.attendance.domain.entities.AttendanceRecord;
import com.deepanshu.attendance.domain.entities.AttendanceSession;
import com.deepanshu.attendance.domain.entities.Subject;
import com.deepanshu.attendance.enums.Status;
import com.deepanshu.attendance.exceptions.ResourceNotFoundException;
import com.deepanshu.attendance.repositories.AttendanceRecordRepository;
import com.deepanshu.attendance.repositories.AttendanceSessionRepository;
import com.deepanshu.attendance.repositories.SubjectRepository;
import com.deepanshu.attendance.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final AttendanceSessionRepository attendanceSessionRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;

    @Override
    public Subject getSubjectFromSubjectCode(String subjectCode) {
        return subjectRepository.findById(subjectCode).orElseThrow(()->new ResourceNotFoundException("Subject Not Found"));
    }

    @Override
    public Long getTotalClassesOfASubject(Subject subject) {
        return attendanceSessionRepository.countBySubject(subject);
    }

    @Override
    public Long getNoOfCLassesOfASubjectAttendedByAStudent(Subject subject, Long rollNo) {
        return attendanceRecordRepository.countByAttendanceSession_SubjectAndStudent_RollNo(subject,rollNo);
    }

    @Override
    public List<DetailedAttendanceResponse.AttendanceHistory> getAttendanceHistory(Subject subject, Long rollNo) {
        List<AttendanceSession> listOfTotalClasses = attendanceSessionRepository.findBySubject_SubjectCodeOrderBySessionDateDesc(subject.getSubjectCode());
        List<AttendanceRecord> listOfAttendedClasses = attendanceRecordRepository.findByAttendanceSession_SubjectAndStudent_RollNo(subject,rollNo);
        return listOfTotalClasses.stream()
                .map(session->{
                    boolean attended = listOfAttendedClasses.stream().anyMatch(record->record.getAttendanceSession().getId().equals(session.getId()));
                    return DetailedAttendanceResponse.AttendanceHistory.builder()
                            .date(session.getSessionDate())
                            .status(attended? Status.PRESENT:Status.ABSENT)
                            .build();
                })
                .toList();
    }
}
