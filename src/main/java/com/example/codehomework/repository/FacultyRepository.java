package com.example.codehomework.repository;

import com.example.codehomework.model.Faculty;
import com.example.codehomework.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

   Faculty getFacultyById(int id);

    List<Faculty> findByColourIgnoreCase(String colour);

    List<Faculty> findAllByColourIgnoreCaseOrNameIgnoreCase(String colour, String name);

    List<Faculty> findAllFacultiesByStudent(int id);

    Faculty studentsByFaculty(int id);
}
