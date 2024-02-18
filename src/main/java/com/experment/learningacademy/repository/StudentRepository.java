package com.experment.learningacademy.repository;

import com.experment.learningacademy.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student ,Integer> {
}
