package com.example.codehomework.handler;

import com.example.codehomework.exception.FacultyNotFoundException;
import com.example.codehomework.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handelStudentNotFoundException(StudentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student not found");
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    public ResponseEntity<String> handelFacultyNotFoundException(FacultyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Faculty not found");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handelConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Bad param requested");
    }
}
