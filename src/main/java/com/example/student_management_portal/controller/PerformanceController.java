package com.example.student_management_portal.controller;

import com.example.student_management_portal.entity.Performance;
import com.example.student_management_portal.entity.Student;
import com.example.student_management_portal.services.PerformanceService;
import com.example.student_management_portal.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/performance")
public class PerformanceController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PerformanceService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public Performance addPerformance(@RequestBody Map<String, String> payload) {
        Long studentId = Long.parseLong(payload.get("studentId"));
        String subject = payload.get("subject");
        int marks = Integer.parseInt(payload.get("marks"));

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        Performance performance = new Performance();
        performance.setStudent(student);
        performance.setSubject(subject);
        performance.setMarks(marks);

        return service.savePerformance(performance);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public List<Performance> getAll() {
        return service.getAll();
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public List<Performance> getByStudent(@PathVariable Long id) {
        return service.getByStudent(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public Performance update(@PathVariable Long id, @RequestBody Performance perf) {
        return service.update(id, perf);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePerformance(@PathVariable Long id) {
        boolean isDeleted = service.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();  // 204 No Content (successful deletion)
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found if performance not found
        }
    }
}
