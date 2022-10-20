package com.example.codehomework.repository;

import com.example.codehomework.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getStudentsByIdStudent(Long idStudent);

    List<Student> findByAge(int age);

    List<Student> findAllStudentsByFaculty(int id);

    List<Student> findAllByAgeBetween(int start, int end);

    @Query(value = "SELECT count(idStudent) FROM students", nativeQuery = true)
    int totalCountOfStudents();

    @Query(value = "SELECT avg(age) FROM students", nativeQuery = true)
    double averageAgeOfStudents();

    @Query(value = "SELECT * FROM students ORDER BY idStudent DESC LIMIT: count", nativeQuery = true)
    List<Student> lastStudents(@Param("count") int count);
}
