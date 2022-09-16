package com.example.codehomework.controller;

import com.example.codehomework.model.Faculty;
import com.example.codehomework.model.Student;
import com.example.codehomework.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
@RequestMapping("student")
@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity <Student>  getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public ResponseEntity <Student>  updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity <?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> studentFilter(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.studentFilter(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> studentFilterByAge(@RequestParam(required = false) int start,
                                                                  @RequestParam(required = false) int end) {
        if (start > 0 || end > 0) {
            return ResponseEntity.ok(studentService.studentFilterByAge(start, end));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> findAllFacultiesByStudent(@RequestParam(required = false) int id) {
        return ResponseEntity.ok(studentService.findAllFacultiesByStudent(id));
    }
    // hi
}
