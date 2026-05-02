package com.deepanshu.attendance.services.impl;

import com.deepanshu.attendance.domain.entities.Enrollment;
import com.deepanshu.attendance.exceptions.ResourceNotFoundException;
import com.deepanshu.attendance.repositories.EnrollmentRepository;
import com.deepanshu.attendance.services.EnrollmentService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment getEnrollmentFromId(Long id) {
        return enrollmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Enrollment not found"));
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    public void delete(Long enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }
}
