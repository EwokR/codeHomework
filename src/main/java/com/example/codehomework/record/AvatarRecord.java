package com.example.codehomework.record;

public class AvatarRecord {

    private Long id;
    private StudentRecord studentRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentRecord getStudentRecord() {
        return studentRecord;
    }

    public void setStudentRecord(StudentRecord studentRecord) {
        this.studentRecord = studentRecord;
    }
}
