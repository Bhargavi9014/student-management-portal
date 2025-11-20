package com.example.student_management_portal.repository;


import com.example.student_management_portal.entity.Attendance;
import com.example.student_management_portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(Student student);
}
