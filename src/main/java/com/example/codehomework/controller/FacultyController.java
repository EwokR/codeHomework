package com.example.codehomework.controller;

import com.example.codehomework.entity.Faculty;
import com.example.codehomework.entity.Student;
import com.example.codehomework.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RequestMapping("faculties")
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

    @GetMapping(params = "colour")
    public ResponseEntity<Collection<Faculty>> facultyFilter(@RequestParam(required = false) String colour) {
        if (colour != null && !colour.isBlank()) {
            return ResponseEntity.ok(facultyService.facultyFilter(colour));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> studentsByFaculty(@RequestParam(required = false) int id) {
        return ResponseEntity.ok(facultyService.studentsByFaculty(id));
    }

    @GetMapping(params = "colourOrName")
    public ResponseEntity<Collection<Faculty>> findAllByColourOrName(@RequestParam(required = false) String colourOrName) {
        if (colourOrName != null && !colourOrName.isBlank()) {
            return ResponseEntity.ok(facultyService.findAllByColourIgnoreCaseOrNameIgnoreCase(colourOrName, colourOrName));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
