package com.deepanshu.attendance.services;

import com.deepanshu.attendance.domain.dtos.SessionDetails;
import com.deepanshu.attendance.domain.entities.AttendanceRecord;
import com.deepanshu.attendance.domain.entities.AttendanceSession;

public interface AttendanceService {
    AttendanceSession getSessionFromSessionId(Long sessionId);
    SessionDetails getDetailedSessionDTO(Long sessionId);
    AttendanceSession createNewSession(String teacherId,String subjectCode);
    void stopSession(Long sessionId);
    AttendanceSession getNewToken(Long sessionId);
    AttendanceRecord markAttendance(String token,Long rollNo);
}
