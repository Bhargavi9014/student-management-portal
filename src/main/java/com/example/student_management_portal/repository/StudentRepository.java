package com.example.student_management_portal.repository;

import com.example.student_management_portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> { }
