package com.example.codehomework.model;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStudent;
    private String name;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    private Faculty faculty;

    public Student(Long idStudent, String name, int age) {
        this.idStudent= idStudent;
        this.name = name;
        this.age = age;

    }

    public Student() {

    }

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Faculty getFaculty() {
        return faculty;
    }
}
