package com.example.codehomework.service;

import com.example.codehomework.entity.Faculty;
import com.example.codehomework.entity.Student;
import com.example.codehomework.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.getById(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        Optional<Faculty> one = facultyRepository.findById(faculty.getIdFaculty());
        if (one.isPresent()) {
            Faculty facultyFromDB = one.get();
            facultyFromDB.setColour(faculty.getColour());
            facultyFromDB.setName(faculty.getName());
            return facultyRepository.save(facultyFromDB);
        } else {
            return null;
        }
    }

    public void deleteFaculty(Long id) {
         facultyRepository.deleteById(id);
    }

    public Collection<Faculty> facultyFilter(String colour) {
        return facultyRepository.findByColourIgnoreCase(colour);
    }

    public Collection<Faculty> findAllByColourIgnoreCaseOrNameIgnoreCase(String colour, String name) {
        return facultyRepository.findAllByColourIgnoreCaseOrNameIgnoreCase(colour, name);
    }


    public Collection<Student> studentsByFaculty(int id) {
        return facultyRepository.getFacultyByIdFaculty(id).getStudents();
    }
}
