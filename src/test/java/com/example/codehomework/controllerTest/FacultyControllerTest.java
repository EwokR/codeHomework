package com.example.codehomework.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.codehomework.controller.FacultyController;
import com.example.codehomework.entity.Faculty;
import com.example.codehomework.repository.FacultyRepository;
import com.example.codehomework.service.FacultyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void createFacultiesTest() throws Exception {
        final String name = "testGreenFaculty";
        final String colour = "green";
        final long idFaculty = 1L;

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColour(colour);
        faculty.setIdFaculty(idFaculty);


        ObjectMapper objectMapper = new ObjectMapper();

        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(eq(idFaculty))).thenReturn(Optional.of(faculty));
        when(facultyRepository.findByColourIgnoreCase(eq(colour))).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Faculty.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    faculty.setIdFaculty(facultyResult.getIdFaculty());
                    assertThat(facultyResult).isNotNull();
                    assertThat(facultyResult).usingRecursiveComparison().isEqualTo(faculty);
                    assertThat(facultyResult.getIdFaculty()).isEqualTo(faculty.getIdFaculty());
                });
    }

    @Test
    public void getFacultyTest() throws Exception {
        final String name = "testGreenFaculty";
        final String colour = "green";
        final long idFaculty = 1L;

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColour(colour);
        faculty.setIdFaculty(idFaculty);


        ObjectMapper objectMapper = new ObjectMapper();

        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(eq(idFaculty))).thenReturn(Optional.of(faculty));
        when(facultyRepository.findByColourIgnoreCase(eq(colour))).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Faculty.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    faculty.setIdFaculty(facultyResult.getIdFaculty());
                    assertThat(facultyResult).isNotNull();
                    assertThat(facultyResult).usingRecursiveComparison().isEqualTo(faculty);
                    assertThat(facultyResult.getIdFaculty()).isEqualTo(faculty.getIdFaculty());
                });

        when(facultyRepository.getById(eq(idFaculty))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + faculty.getIdFaculty()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Faculty.class);
                    assertThat(facultyResult).isNotNull();
                    assertThat(facultyResult).usingRecursiveComparison().isEqualTo(faculty);
                });
    }

    @Test
    public void updateFacultyTest() throws Exception {
        final String name = "testGreenFaculty";
        final String colour = "green";
        final long idFaculty = 1L;

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColour(colour);
        faculty.setIdFaculty(idFaculty);


        ObjectMapper objectMapper = new ObjectMapper();

        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(eq(idFaculty))).thenReturn(Optional.of(faculty));
        when(facultyRepository.findByColourIgnoreCase(eq(colour))).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Faculty.class);
                    faculty.setIdFaculty(facultyResult.getIdFaculty());
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    assertThat(facultyResult).isNotNull();
                    assertThat(facultyResult).usingRecursiveComparison().isEqualTo(faculty);
                    assertThat(facultyResult.getIdFaculty()).isEqualTo(faculty.getIdFaculty());
                });

        when(facultyRepository.getById(eq(idFaculty))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + faculty.getIdFaculty()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Faculty.class);
                    assertThat(facultyResult.getIdFaculty()).isNotNull();
                    assertThat(facultyResult).usingRecursiveComparison().isEqualTo(faculty);
                    faculty.setColour("yellow");
                });

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Faculty.class);
                    assertThat(facultyResult).isNotNull();
                    assertThat(facultyResult).usingRecursiveComparison().ignoringFields("idFaculty", "colour").isEqualTo(faculty);
                    assertThat(facultyResult.getIdFaculty()).isEqualTo(faculty.getIdFaculty());
                });

        when(facultyRepository.getById(eq(idFaculty))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + faculty.getIdFaculty()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Faculty.class);
                    assertThat(facultyResult).isNotNull();
                    assertThat(facultyResult.getColour()).isEqualTo("yellow");
                    assertThat(facultyResult).usingRecursiveComparison().isEqualTo(faculty);
                });
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        final String name = "testGreenFaculty";
        final String colour = "green";
        final long idFaculty = 1L;

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColour(colour);
        faculty.setIdFaculty(idFaculty);


        ObjectMapper objectMapper = new ObjectMapper();

        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(eq(idFaculty))).thenReturn(Optional.of(faculty));
        when(facultyRepository.findByColourIgnoreCase(eq(colour))).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Faculty facultyResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Faculty.class);
                    faculty.setIdFaculty(facultyResult.getIdFaculty());
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    assertThat(facultyResult).isNotNull();
                    assertThat(facultyResult).usingRecursiveComparison().isEqualTo(faculty);
                    assertThat(facultyResult.getIdFaculty()).isEqualTo(faculty.getIdFaculty());
                });

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/" + faculty.getIdFaculty())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + faculty.getIdFaculty()))
                .andExpect(status().is(404));
    }
}
