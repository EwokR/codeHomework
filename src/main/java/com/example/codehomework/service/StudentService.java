package com.example.codehomework.service;

import com.example.codehomework.model.Faculty;
import com.example.codehomework.model.Student;
import com.example.codehomework.repository.FacultyRepository;
import com.example.codehomework.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
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

    public Student updateStudent(Student student) {
        Optional<Student> one = studentRepository.findById(student.getIdStudent());
        if (one.isPresent()) {
            Student studentFromMyDB = one.get();
            studentFromMyDB.setAge(student.getAge());
            studentFromMyDB.setName(student.getName());
            return studentRepository.save(studentFromMyDB);
        } else {
            return null;
        }
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
}