package com.example.student_management_portal.services;

import com.example.student_management_portal.entity.Performance;
import com.example.student_management_portal.entity.Student;
import com.example.student_management_portal.exception.ResourceNotFoundException;
import com.example.student_management_portal.repository.PerformanceRepository;
import com.example.student_management_portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PerformanceService {

    @Autowired
    private PerformanceRepository repo;

    @Autowired
    private StudentRepository studentRepo;

    public Performance savePerformance(Performance performance) {
        return repo.save(performance);
    }

    public List<Performance> getAll() {
        return repo.findAll();
    }

    public List<Performance> getByStudent(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return repo.findByStudent(student);
    }

    public Performance update(Long id, Performance perf) {
        Performance existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance not found"));
        existing.setMarks(perf.getMarks());
        existing.setSubject(perf.getSubject());
        return repo.save(existing);
    }

    public boolean delete(Long id) {
        repo.deleteById(id);
        return true;
    }

}
