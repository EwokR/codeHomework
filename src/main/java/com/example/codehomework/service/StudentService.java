package com.example.codehomework.service;

import com.example.codehomework.component.RecordComponent;
import com.example.codehomework.exception.FacultyNotFoundException;
import com.example.codehomework.exception.StudentNotFoundException;
import com.example.codehomework.entity.Faculty;
import com.example.codehomework.entity.Student;
import com.example.codehomework.record.StudentRecord;
import com.example.codehomework.repository.FacultyRepository;
import com.example.codehomework.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Spliterator;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final RecordComponent recordComponent;

    private final FacultyRepository facultyRepository;

    private int count = 1;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository,
                          RecordComponent recordComponent) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.recordComponent = recordComponent;
    }

    public StudentRecord createStudent(StudentRecord studentRecord) {
        logger.debug("Creat student: {}", studentRecord);
        Student student = recordComponent.toEntityStudentRecord(studentRecord);
        if (studentRecord.getFaculty() != null) {
            Faculty faculty = facultyRepository.findById(studentRecord.getFaculty().getId()).orElseThrow(FacultyNotFoundException::new);
            student.setFaculty(faculty);
        }
        logger.debug("Creat student as: {}", student);
        return recordComponent.toRecordStudent(studentRepository.save(student));
    }

    public Student getStudentById(Long id) {
        logger.debug("Get student by id: {}", id);
        Optional<Student> one = studentRepository.findById(id);
        if (one.isPresent()) {
            Student studentGet = one.get();
            logger.debug("Student by id: {}", id, one);
            return studentGet;
        } else {
            logger.error("Student by id not found:{}", StudentNotFoundException.class);
            return null;
        }
    }

    public StudentRecord updateStudent(StudentRecord studentRecord) {
        logger.debug("Update Student: {}", studentRecord);
        Student oldStudent = studentRepository.findById(studentRecord.getId())
                .orElseThrow(StudentNotFoundException::new);
        oldStudent.setAge(studentRecord.getAge());
        oldStudent.setName(studentRecord.getName());
        logger.debug("Student updated");
        return recordComponent.toRecordStudent(studentRepository.save(oldStudent));
    }

    public void deleteStudent(Long id) {
        logger.info("Delete student by id");
        studentRepository.deleteById(id);
    }

    public Collection<Student> studentFilter(Integer age) {
        logger.info("Get student by age, now outdated because most of student have same age");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> studentFilterByAge(Integer start, Integer end) {
        logger.info("Get students by age in intervals made by users, now outdated because most of student have same age");
        return studentRepository.findAllByAgeBetween(start, end);
    }

    public Faculty findAllFacultiesByStudent(Long idStudent) {
        logger.info("Get student by their faculty");
        return studentRepository.getStudentsByIdStudent(idStudent).getFaculty();
    }

    public int totalCountOfStudent() {
        logger.info("How much students in school now.");
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        logger.info("Get students average age, now outdated because most of student have same age");
        return studentRepository.averageAgeOfStudents();
    }

    public List<StudentRecord> lastStudents(int count) {
        logger.info("Shows last student or students");
        return studentRepository.lastStudents(count).stream()
                .map(recordComponent::toRecordStudent)
                .collect(Collectors.toList());
    }

    public List<String> findAllStudentsWitchStartsOnChar(char ch) {
        List<Student> sList = studentRepository.findAll();
        String str = (ch + "").toUpperCase();
        return sList.stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith(str))
                .sorted()
                .collect(Collectors.toList());
    }

    public double findAverageAgeOfStudentsByStream() {
        return (double) studentRepository.findAll()
                .stream()
                .mapToDouble(Student::getAge)
                .average().orElse(0);
    }

    public int numberFinder() {
        int sum = Stream.iterate(1, a -> a + 1).parallel().limit(1_000_000).reduce(0, (a, b) -> a + b);
        return sum;
    }

    public String allStudentsToConsoleOutput() {
        List<Student> sList = studentRepository.findAllSortedByIdStudent();

        new Thread(() -> studentConsoleOut(sList.get(2), sList.get(3))).start();
        new Thread(() -> studentConsoleOut(sList.get(4), sList.get(5))).start();

        studentConsoleOut(sList.get(0), sList.get(1));
        studentConsoleOut(sList.get(6), sList.get(7));

        return "Ok";
    }

    public String allStudentsToConsoleSyncOutput() {
        List<Student> sList = studentRepository.findAllSortedByIdStudent();

        new Thread(() -> studentConsoleSyncOut(sList.get(2), sList.get(3))).start();
        new Thread(() -> studentConsoleSyncOut(sList.get(4), sList.get(5))).start();

        studentConsoleSyncOut(sList.get(0), sList.get(1));
        studentConsoleSyncOut(sList.get(6), sList.get(7));

        return "Ok";
    }

    private void studentConsoleOut(Student student1, Student student2) {
        try {
            System.out.println(student1);
            System.out.println(student2);
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Thread was interrupted");
        }
    }

    private synchronized void studentConsoleSyncOut(Student student1, Student student2) {
        try {
            System.out.println(student1);
            System.out.println(student2);
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Thread was interrupted");
        }
    }
}