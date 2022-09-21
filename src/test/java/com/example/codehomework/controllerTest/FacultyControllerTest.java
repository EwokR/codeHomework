package com.example.codehomework.controllerTest;


import com.example.codehomework.controller.FacultyController;
import com.example.codehomework.model.Faculty;
import com.example.codehomework.repository.FacultyRepository;
import com.example.codehomework.service.FacultyService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void testFaculties() throws Exception {
        final String name = "testGreenFaculty";
        final String colour = "green";
        final long id = 1;

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColour(colour);
        faculty.setIdFaculty(id);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("colour", colour);
        facultyObject.put("id", id);

        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(eq(id))).thenReturn(Optional.of(faculty));
        when(facultyRepository.findByColourIgnoreCase(eq(colour))).thenReturn(Collections.singleton(faculty));
    }
}
