package com.example.codehomework.repository;

import com.example.codehomework.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    List<Faculty> findByColour(String colour);
}
