package com.example.student_management_portal.services;

import com.example.student_management_portal.entity.Student;
import com.example.student_management_portal.exception.ResourceNotFoundException;
import com.example.student_management_portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Optional<Student> getById(Long id) {
        return repo.findById(id);
    }

    public Student create(Student student) {
        return repo.save(student);
    }

    public Student update(Long id, Student student) {
        Student existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        existing.setFirstName(student.getFirstName());
        existing.setLastName(student.getLastName());
        existing.setDob(student.getDob());
        existing.setCourse(student.getCourse());
        existing.setEmail(student.getEmail());
        return repo.save(existing);
    }

    public boolean delete(Long id) {
        repo.deleteById(id);
        return true;
    }

    public Student getStudentById(Long id) {
        return repo.findById(id)
                .orElse(null);
    }
}
