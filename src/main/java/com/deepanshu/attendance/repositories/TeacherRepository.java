package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher,String> {
    Optional<Teacher> findByUser_Id(UUID id);
}
