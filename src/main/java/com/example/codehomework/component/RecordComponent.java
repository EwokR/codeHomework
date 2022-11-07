package com.example.codehomework.component;

import com.example.codehomework.entity.Avatar;
import com.example.codehomework.entity.Faculty;
import com.example.codehomework.entity.Student;
import com.example.codehomework.record.AvatarRecord;
import com.example.codehomework.record.FacultyRecord;
import com.example.codehomework.record.StudentRecord;
import org.springframework.stereotype.Component;

@Component
public class RecordComponent {

    public StudentRecord toRecordStudent(Student student) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setId(student.getIdStudent());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());
        if (student.getFaculty() != null) {
            studentRecord.setFaculty(toRecordFaculty(student.getFaculty()));
        }
        return studentRecord;
    }

    public FacultyRecord toRecordFaculty(Faculty faculty) {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setId(faculty.getIdFaculty());
        facultyRecord.setName(faculty.getName());
        facultyRecord.setColour(faculty.getColour());
        return facultyRecord;
    }

    public AvatarRecord toRecordAvatar(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatar.getIdAvatar());
        avatarRecord.setStudentRecord(toRecordStudent(avatar.getStudent()));
        return avatarRecord;
    }

    public Student toEntityStudentRecord(StudentRecord studentRecord) {
        Student student = new Student();
        student.setAge(studentRecord.getAge());
        student.setName(studentRecord.getName());
        return student;
    }

    public Faculty toEntityFacultyRecord(FacultyRecord facultyRecord) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyRecord.getName());
        faculty.setColour(facultyRecord.getColour());
        return faculty;
    }
}
