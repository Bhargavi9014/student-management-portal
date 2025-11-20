package com.example.student_management_portal.controller;

import com.example.student_management_portal.entity.Attendance;
import com.example.student_management_portal.entity.Student;
import com.example.student_management_portal.services.AttendanceService;
import com.example.student_management_portal.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public Attendance mark(@RequestBody Map<String, String> payload) {
        Long studentId = Long.parseLong(payload.get("studentId"));
        String status = payload.get("status");
        String dateStr = payload.get("date");

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setStatus(status);
        attendance.setDate(LocalDate.parse(dateStr));

        return attendanceService.save(attendance);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public List<Attendance> getAll() {
        return attendanceService.getAll();
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public List<Attendance> getByStudent(@PathVariable Long id) {
        return attendanceService.getByStudent(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        boolean isDeleted = attendanceService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();  // 204 No Content (successful deletion)
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found if attendance not found
        }
    }
}
