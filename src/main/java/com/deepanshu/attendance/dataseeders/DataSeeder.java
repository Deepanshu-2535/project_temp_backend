package com.deepanshu.attendance.dataseeders;

import com.deepanshu.attendance.domain.entities.*;
import com.deepanshu.attendance.enums.Role;
import com.deepanshu.attendance.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceSessionRepository attendanceSessionRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("student1@college.edu").isPresent()) {
            System.out.println("Data already seeded, skipping...");
            return;
        }

        // ── Teachers ──────────────────────────────────────────────

        User teacherUser1 = createUser("teacher1@college.edu");
        User teacherUser2 = createUser("teacher2@college.edu");
        User teacherUser3 = createUser("teacher3@college.edu");
        User teacherUser4 = createUser("teacher4@college.edu");
        User teacherUser5 = createUser("teacher5@college.edu");

        Teacher teacher1 = createTeacher("TCH001", teacherUser1, "Dr.", "Rajesh",  "Kumar",   "Professor",            "Computer Science");
        Teacher teacher2 = createTeacher("TCH002", teacherUser2, "Dr.", "Priya",   "Sharma",  "Associate Professor",  "Computer Science");
        Teacher teacher3 = createTeacher("TCH003", teacherUser3, "Mr.", "Amit",    "Verma",   "Assistant Professor",  "Computer Science");
        Teacher teacher4 = createTeacher("TCH004", teacherUser4, "Dr.", "Sunita",  "Patel",   "Professor",            "Computer Science");
        Teacher teacher5 = createTeacher("TCH005", teacherUser5, "Ms.", "Neha",    "Gupta",   "Lecturer",             "Computer Science");

        // ── Subjects ──────────────────────────────────────────────

        Subject ds   = createSubject("CS101", "Data Structures",       teacher1, 5);
        Subject os   = createSubject("CS102", "Operating Systems",     teacher2, 5);
        Subject dbms = createSubject("CS103", "Database Management",   teacher3, 5);
        Subject cn   = createSubject("CS104", "Computer Networks",     teacher4, 5);
        Subject se   = createSubject("CS105", "Software Engineering",  teacher5, 5);

        // ── Students ──────────────────────────────────────────────

        User studentUser1 = createUser("student1@college.edu");
        User studentUser2 = createUser("student2@college.edu");
        User studentUser3 = createUser("student3@college.edu");
        User studentUser4 = createUser("student4@college.edu");
        User studentUser5 = createUser("student5@college.edu");

        Student student1 = createStudent(2311113L, studentUser1, "Arya",   "Jain",   6, "Computer Science");
        Student student2 = createStudent(2311135L, studentUser2, "Harshita",   "Patidar",   6, "Computer Science");
        Student student3 = createStudent(2311125L, studentUser3, "Deepanshu",   "Gupta",  6, "Computer Science");
        Student student4 = createStudent(2311174L, studentUser4, "Sneha",   "Verma",   6, "Computer Science");
        Student student5 = createStudent(2311178L, studentUser5, "Karan",   "Patel",   6, "Computer Science");

        List<Student> allStudents = List.of(student1, student2, student3, student4, student5);

        // ── Enroll all students in all subjects ───────────────────

        for (Student student : allStudents) {
            enrollmentRepository.save(Enrollment.builder().student(student).subject(ds).build());
            enrollmentRepository.save(Enrollment.builder().student(student).subject(os).build());
            enrollmentRepository.save(Enrollment.builder().student(student).subject(dbms).build());
            enrollmentRepository.save(Enrollment.builder().student(student).subject(cn).build());
            enrollmentRepository.save(Enrollment.builder().student(student).subject(se).build());
        }

        // ── Attendance Sessions (5 per subject = 25 total) ────────

        // Data Structures sessions
        AttendanceSession ds1  = createSession(ds,   teacher1, LocalDate.of(2026, 1, 10));
        AttendanceSession ds2  = createSession(ds,   teacher1, LocalDate.of(2026, 1, 17));
        AttendanceSession ds3  = createSession(ds,   teacher1, LocalDate.of(2026, 1, 24));
        AttendanceSession ds4  = createSession(ds,   teacher1, LocalDate.of(2026, 2, 7));
        AttendanceSession ds5  = createSession(ds,   teacher1, LocalDate.of(2026, 2, 14));

        // Operating Systems sessions
        AttendanceSession os1  = createSession(os,   teacher2, LocalDate.of(2026, 1, 11));
        AttendanceSession os2  = createSession(os,   teacher2, LocalDate.of(2026, 1, 18));
        AttendanceSession os3  = createSession(os,   teacher2, LocalDate.of(2026, 1, 25));
        AttendanceSession os4  = createSession(os,   teacher2, LocalDate.of(2026, 2, 8));
        AttendanceSession os5  = createSession(os,   teacher2, LocalDate.of(2026, 2, 15));

        // DBMS sessions
        AttendanceSession db1  = createSession(dbms, teacher3, LocalDate.of(2026, 1, 12));
        AttendanceSession db2  = createSession(dbms, teacher3, LocalDate.of(2026, 1, 19));
        AttendanceSession db3  = createSession(dbms, teacher3, LocalDate.of(2026, 1, 26));
        AttendanceSession db4  = createSession(dbms, teacher3, LocalDate.of(2026, 2, 9));
        AttendanceSession db5  = createSession(dbms, teacher3, LocalDate.of(2026, 2, 16));

        // Computer Networks sessions
        AttendanceSession cn1  = createSession(cn,   teacher4, LocalDate.of(2026, 1, 13));
        AttendanceSession cn2  = createSession(cn,   teacher4, LocalDate.of(2026, 1, 20));
        AttendanceSession cn3  = createSession(cn,   teacher4, LocalDate.of(2026, 1, 27));
        AttendanceSession cn4  = createSession(cn,   teacher4, LocalDate.of(2026, 2, 10));
        AttendanceSession cn5  = createSession(cn,   teacher4, LocalDate.of(2026, 2, 17));

        // Software Engineering sessions
        AttendanceSession se1  = createSession(se,   teacher5, LocalDate.of(2026, 1, 14));
        AttendanceSession se2  = createSession(se,   teacher5, LocalDate.of(2026, 1, 21));
        AttendanceSession se3  = createSession(se,   teacher5, LocalDate.of(2026, 1, 28));
        AttendanceSession se4  = createSession(se,   teacher5, LocalDate.of(2026, 2, 11));
        AttendanceSession se5  = createSession(se,   teacher5, LocalDate.of(2026, 2, 18));

        // ── Attendance Records ─────────────────────────────────────
        // Each row = which students attended that session
        // true = present, false = absent (no record created)

        // Data Structures
        markAttendance(ds1,  allStudents, List.of(true,  true,  true,  true,  true));
        markAttendance(ds2,  allStudents, List.of(true,  true,  false, true,  true));
        markAttendance(ds3,  allStudents, List.of(true,  false, true,  true,  false));
        markAttendance(ds4,  allStudents, List.of(false, true,  true,  false, true));
        markAttendance(ds5,  allStudents, List.of(true,  true,  true,  true,  false));

        // Operating Systems
        markAttendance(os1,  allStudents, List.of(true,  true,  true,  false, true));
        markAttendance(os2,  allStudents, List.of(true,  false, true,  true,  true));
        markAttendance(os3,  allStudents, List.of(false, true,  false, true,  true));
        markAttendance(os4,  allStudents, List.of(true,  true,  true,  true,  false));
        markAttendance(os5,  allStudents, List.of(true,  true,  false, false, true));

        // DBMS
        markAttendance(db1,  allStudents, List.of(true,  true,  true,  true,  true));
        markAttendance(db2,  allStudents, List.of(false, true,  true,  true,  true));
        markAttendance(db3,  allStudents, List.of(true,  false, true,  false, true));
        markAttendance(db4,  allStudents, List.of(true,  true,  false, true,  false));
        markAttendance(db5,  allStudents, List.of(true,  true,  true,  true,  true));

        // Computer Networks
        markAttendance(cn1,  allStudents, List.of(false, true,  true,  true,  true));
        markAttendance(cn2,  allStudents, List.of(true,  true,  false, true,  true));
        markAttendance(cn3,  allStudents, List.of(true,  false, true,  true,  false));
        markAttendance(cn4,  allStudents, List.of(true,  true,  true,  false, true));
        markAttendance(cn5,  allStudents, List.of(false, true,  true,  true,  true));

        // Software Engineering
        markAttendance(se1,  allStudents, List.of(true,  true,  true,  true,  false));
        markAttendance(se2,  allStudents, List.of(true,  false, true,  true,  true));
        markAttendance(se3,  allStudents, List.of(false, true,  false, true,  true));
        markAttendance(se4,  allStudents, List.of(true,  true,  true,  false, true));
        markAttendance(se5,  allStudents, List.of(true,  true,  true,  true,  true));

        System.out.println("✅ Data seeded successfully!");
        System.out.println("   5 teachers, 5 students, 5 subjects, 25 sessions created");
        System.out.println("   Login with student1@college.edu / password123");
        System.out.println("   Login with teacher1@college.edu / password123");
    }

    // ── Helper Methods ─────────────────────────────────────────────

    private User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode("password123"));
        user.setRole(email.startsWith("student") ? Role.STUDENT : Role.TEACHER);
        return userRepository.save(user);
    }

    private Teacher createTeacher(String id, User user, String title,
                                  String firstName, String lastName,
                                  String designation, String department) {
        return teacherRepository.save(Teacher.builder()
                .teacherId(id)
                .user(user)
                .title(title)
                .firstName(firstName)
                .lastName(lastName)
                .designation(designation)
                .department(department)
                .build());
    }

    private Subject createSubject(String code, String name, Teacher teacher, Integer semester) {
        return subjectRepository.save(Subject.builder()
                .subjectCode(code)
                .subjectName(name)
                .teacher(teacher)
                .semester(semester)
                .department("Computer Science")
                .build());
    }

    private Student createStudent(Long rollNo, User user, String firstName,
                                  String lastName, Integer semester, String department) {
        return studentRepository.save(Student.builder()
                .rollNo(rollNo)
                .user(user)
                .firstName(firstName)
                .lastName(lastName)
                .semester(semester)
                .department(department)
                .build());
    }

    private AttendanceSession createSession(Subject subject, Teacher teacher, LocalDate date) {
        return attendanceSessionRepository.save(AttendanceSession.builder()
                .subject(subject)
                .teacher(teacher)
                .sessionDate(date)
                .currentToken(UUID.randomUUID().toString())
                .tokenExpiresAt(LocalDateTime.now().minusHours(1))
                .isActive(false)
                .build());
    }

    private void markAttendance(AttendanceSession session,
                                List<Student> students,
                                List<Boolean> attendance) {
        for (int i = 0; i < students.size(); i++) {
            if (attendance.get(i)) {
                attendanceRecordRepository.save(AttendanceRecord.builder()
                        .attendanceSession(session)
                        .student(students.get(i))
                        .scannedAt(LocalDateTime.now())
                        .build());
            }
        }
    }
}