package com.example.codehomework.controllerTest;

import com.example.codehomework.controller.StudentController;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.codehomework.model.Student;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getStudentTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void createStudentTest() throws Exception {
        Student student = new Student();
        student.setName("Magic-guy");
        student.setAge(200);
        student.setIdStudent(6L);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);

        ResponseEntity<Student> response = testRestTemplate.exchange("/student", HttpMethod.POST, entity, Student.class,
                student.getIdStudent());

        assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Magic-guy");
        assertThat(response.getBody().getAge()).isEqualTo(student.getAge());
        assertThat(response.getBody().getIdStudent()).isEqualTo(student.getIdStudent());

    }

    @Test
    public void updateStudentTest() throws Exception {
        Student student = new Student();
        student.setName("Magic-guy");
        student.setAge(200);
        student.setIdStudent(6L);
        student.setAge(29);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);

        ResponseEntity<Student> response = testRestTemplate.exchange("/student/", HttpMethod.PUT, entity, Student.class,
                student.getIdStudent());

        assertThat(response.getBody().getAge()).isNotEqualTo(200);
        assertThat(response.getBody().getAge()).isEqualTo(29);
        assertThat(response.getBody().getName()).isEqualTo(student.getName());
        assertThat(response.getBody().getIdStudent()).isEqualTo(student.getIdStudent());

    }

    @Test
    public void deleteStudentTest() throws Exception {
        Student student = new Student();
        student.setName("Magic-guy");
        student.setAge(12);
        student.setIdStudent(6L);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);

        ResponseEntity<Student> response = testRestTemplate.exchange("/student/{id}", HttpMethod.POST, entity, Student.class,
                student.getIdStudent());

        this.testRestTemplate.delete("http://localhost:" + port + "/student", student, Student.class);

        assertThat(response.getBody().getIdStudent()).isNull();
        assertThat(response.getBody().getAge()).isNull();
        assertThat(response.getBody().getName()).isNull();
    }
}
