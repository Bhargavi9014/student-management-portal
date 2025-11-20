package com.example.student_management_portal.services;

import com.example.student_management_portal.entity.Attendance;
import com.example.student_management_portal.entity.Student;
import com.example.student_management_portal.exception.ResourceNotFoundException;
import com.example.student_management_portal.repository.AttendanceRepository;
import com.example.student_management_portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository repo;

    @Autowired
    private StudentRepository studentRepo;

    public Attendance save(Attendance att) {
        Student student = studentRepo.findById(att.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        att.setStudent(student);
        return repo.save(att);
    }

    public List<Attendance> getAll() {
        return repo.findAll();
    }

    public List<Attendance> getByStudent(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return repo.findByStudent(student);
    }

    public boolean delete(Long id) {
        repo.deleteById(id);
        return true;
    }

}
