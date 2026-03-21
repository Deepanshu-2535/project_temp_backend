package com.deepanshu.attendance.services;

import com.deepanshu.attendance.domain.dtos.SessionDetails;
import com.deepanshu.attendance.domain.entities.AttendanceSession;

public interface AttendanceSessionService {
    AttendanceSession getSessionFromSessionId(Long sessionId);
    SessionDetails getDetailedSessionDTO(Long sessionId);
    AttendanceSession createNewSession(String teacherId,String subjectCode);
    void stopSession(Long sessionId);
    AttendanceSession getNewToken(Long sessionId);
}
