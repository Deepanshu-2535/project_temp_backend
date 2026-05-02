package com.deepanshu.attendance.services;

import com.deepanshu.attendance.domain.entities.Enrollment;

import java.util.List;

public interface EnrollmentService {
    Enrollment getEnrollmentFromId(Long id);
    Enrollment save(Enrollment enrollment);
    List<Enrollment> findAll();
    void delete(Long enrollmentId);

}
