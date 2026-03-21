package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.Enrollment;
import com.deepanshu.attendance.domain.entities.Student;
import com.deepanshu.attendance.domain.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    @Query("SELECT e.subject FROM Enrollment e WHERE e.student.rollNo = :rollNo")
    List<Subject> findSubjectByStudent_RollNo(Long rollNo);
    @Query("SELECT COUNT(DISTINCT e.student) FROM Enrollment e WHERE e.subject.teacher.teacherId = :teacherId")
    Long countDistinctStudentsByTeacherId(String teacherId);
    Long countBySubject(Subject subject);
    @Query("Select e.student FROM Enrollment e WHERE e.subject = :subject")
    List<Student> findBySubject(Subject subject);
}
