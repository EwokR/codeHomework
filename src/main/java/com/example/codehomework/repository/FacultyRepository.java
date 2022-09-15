package com.example.codehomework.repository;

import com.example.codehomework.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    List<Faculty> findByColourIgnoreCase(String colour);

    List<Faculty> findAllByColourIgnoreCaseOrNameIgnoreCase(String colour, String name);
}
