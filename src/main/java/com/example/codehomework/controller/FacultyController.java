package com.example.codehomework.controller;

import com.example.codehomework.model.Faculty;
import com.example.codehomework.model.Student;
import com.example.codehomework.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RequestMapping("faculty")
@RestController
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping()
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> facultyFilter(@RequestParam(required = false) String colour) {
        if (colour != null && !colour.isBlank()) {
            return ResponseEntity.ok(facultyService.facultyFilter(colour));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findAllFacultiesByStudent(@RequestBody Student student) {
        return ResponseEntity.ok(facultyService.findAllFacultiesByStudent(student));
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findAllByColourOrName(@RequestParam(required = false) String colourOrName) {
        if (colourOrName != null && !colourOrName.isBlank()) {
            return ResponseEntity.ok(facultyService.findAllByColourIgnoreCaseOrNameIgnoreCase(colourOrName, colourOrName));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
