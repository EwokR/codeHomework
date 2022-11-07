package com.example.codehomework.repository;

import com.example.codehomework.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

   Faculty getFacultyByIdFaculty(long id);

    List<Faculty> findByColourIgnoreCase(String colour);

    List<Faculty> findAllByColourIgnoreCaseOrNameIgnoreCase(String colour, String name);

    List<Faculty> findAllFacultiesByStudents_idStudent(int id);


}
