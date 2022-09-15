package com.example.codehomework.repository;

import com.example.codehomework.model.Faculty;
import com.example.codehomework.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findByAge(int age);

    List<Student> findAllStudentsByFaculty(Faculty faculty);

    List<Student> findAllByAgeBetween(int start, int end);
}
