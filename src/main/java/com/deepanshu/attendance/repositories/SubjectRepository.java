package com.deepanshu.attendance.repositories;

import com.deepanshu.attendance.domain.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,String> {
}
