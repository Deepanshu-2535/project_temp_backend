package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,String> {
    Long countByTeacher_TeacherId(String teacherId);
    List<Subject> findByTeacher_TeacherId(String teacherId);
}
