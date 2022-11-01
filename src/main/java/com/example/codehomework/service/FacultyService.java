package com.example.codehomework.service;

import com.example.codehomework.entity.Faculty;
import com.example.codehomework.entity.Student;
import com.example.codehomework.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Creat faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.debug("Get faculty by id: {}", id);
        return facultyRepository.getById(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.debug("Update faculty: {}", faculty);
        Optional<Faculty> one = facultyRepository.findById(faculty.getIdFaculty());
        if (one.isPresent()) {
            Faculty facultyFromDB = one.get();
            facultyFromDB.setColour(faculty.getColour());
            facultyFromDB.setName(faculty.getName());
            logger.info("Faculty updated");
            return facultyRepository.save(facultyFromDB);
        } else {
            return null;
        }
    }

    public void deleteFaculty(Long id) {
        logger.debug("Delete faculty by id: {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> facultyFilter(String colour) {
        logger.info("Shows faculties with same colour");
        return facultyRepository.findByColourIgnoreCase(colour);
    }

    public Collection<Faculty> findAllByColourIgnoreCaseOrNameIgnoreCase(String colour, String name) {
        logger.info("Finds faculty by it's colour and/or name");
        return facultyRepository.findAllByColourIgnoreCaseOrNameIgnoreCase(colour, name);
    }


    public Collection<Student> studentsByFaculty(int id) {
        logger.info("Shows students on faculty");
        return facultyRepository.getFacultyByIdFaculty(id).getStudents();
    }

    public String longestFacultyName() {
        Faculty faculty = new Faculty();

        return (String) facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .min(String::compareTo).get();
    }
}
