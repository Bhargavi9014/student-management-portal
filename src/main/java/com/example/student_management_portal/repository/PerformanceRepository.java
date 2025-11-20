package com.example.student_management_portal.repository;

import com.example.student_management_portal.entity.Performance;
import com.example.student_management_portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByStudent(Student student);
}
