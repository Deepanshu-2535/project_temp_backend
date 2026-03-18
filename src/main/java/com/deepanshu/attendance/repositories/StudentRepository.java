package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByUser_Id(UUID id);
}
