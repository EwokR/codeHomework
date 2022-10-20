package com.example.codehomework.service;

import com.example.codehomework.component.RecordComponent;
import com.example.codehomework.exception.FacultyNotFoundException;
import com.example.codehomework.exception.StudentNotFoundException;
import com.example.codehomework.entity.Faculty;
import com.example.codehomework.entity.Student;
import com.example.codehomework.record.StudentRecord;
import com.example.codehomework.repository.FacultyRepository;
import com.example.codehomework.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final RecordComponent recordComponent;

    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository,
                          RecordComponent recordComponent) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.recordComponent = recordComponent;
    }

    public StudentRecord createStudent(StudentRecord studentRecord) {
        Student student = recordComponent.toEntityStudentRecord(studentRecord);
        if (studentRecord.getFaculty() != null) {
            Faculty faculty = facultyRepository.findById(studentRecord.getFaculty().getId()).orElseThrow(FacultyNotFoundException::new);
            student.setFaculty(faculty);
        }
        return recordComponent.toRecordStudent(studentRepository.save(student));
    }

    public Student getStudentById(Long id) {
        Optional<Student> one = studentRepository.findById(id);
        if (one.isPresent()) {
            Student studentGet = one.get();
            return studentGet;
        } else {
            return null;
        }
    }

    public StudentRecord updateStudent(StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(studentRecord.getId())
                .orElseThrow(StudentNotFoundException::new);
        oldStudent.setAge(studentRecord.getAge());
        oldStudent.setName(studentRecord.getName());
        return recordComponent.toRecordStudent(studentRepository.save(oldStudent));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> studentFilter(Integer age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> studentFilterByAge(Integer start, Integer end) {
        return studentRepository.findAllByAgeBetween(start, end);
    }

    public Faculty findAllFacultiesByStudent(Long idStudent) {
        return studentRepository.getStudentsByIdStudent(idStudent).getFaculty();
    }

    public int totalCountOfStudent() {
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        return studentRepository.averageAgeOfStudents();
    }

    public List<StudentRecord> lastStudents(int count) {
        return studentRepository.lastStudents(count).stream()
                .map(recordComponent::toRecordStudent)
                .collect(Collectors.toList());
    }
}