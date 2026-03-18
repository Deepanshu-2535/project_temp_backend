package com.deepanshu.attendance.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attendance_records")
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private AttendanceSession attendanceSession;

    @ManyToOne
    @JoinColumn(name = "student_roll_no")
    private Student student;

    private LocalDateTime scannedAt;
}