package com.example.codehomework.controller;

import com.example.codehomework.entity.Faculty;
import com.example.codehomework.entity.Student;
import com.example.codehomework.record.StudentRecord;
import com.example.codehomework.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequestMapping("students")
@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentRecord> createStudent(@RequestBody StudentRecord studentRecord) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(studentRecord));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public StudentRecord updateStudent(@RequestBody StudentRecord studentRecord) {
        return studentService.updateStudent(studentRecord);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "age")
    public ResponseEntity<Collection<Student>> studentFilter(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.studentFilter(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping(params = {"start", "end"})
    public ResponseEntity<Collection<Student>> studentFilterByAge(@RequestParam(required = false) int start,
                                                                  @RequestParam(required = false) int end) {
        if (start > 0 || end > 0) {
            return ResponseEntity.ok(studentService.studentFilterByAge(start, end));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> findAllFacultiesByStudent(@RequestParam(required = false) long idStudent) {
        return ResponseEntity.ok(studentService.findAllFacultiesByStudent(idStudent));
    }

    @GetMapping("/totalCount")
    public int totalCountOfStudents() {
        return studentService.totalCountOfStudent();
    }

    @GetMapping("/averageAge")
    public double averageAgeOfStudents() {
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/lastStudents")
    public List<StudentRecord> lastStudents(@RequestParam @Min(1) @Max(5) int count) {
        return studentService.lastStudents(count);
    }
}
