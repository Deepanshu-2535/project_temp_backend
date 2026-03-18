package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,String> {
}
