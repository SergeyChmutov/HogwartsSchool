package ru.hogwarts.school.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.constants.Constants.*;

@WebMvcTest
public class FacultyControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void testCreateFaculty() throws Exception {
        final Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, COLOR_RED);
        final JSONObject facultyJson = getJsonObjectOfFaculty(faculty);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + ID_FIELD).value(ID_ZERO))
                .andExpect(jsonPath("$." + NAME_FIELD).value(FACULTY_NAME_GRYFFINDOR))
                .andExpect(jsonPath("$." + COLOR_FIELD).value(COLOR_RED));

        verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    public void testGetFaculty() throws Exception {
        final Faculty faculty = new Faculty(ID_TWO, FACULTY_NAME_SLYTHERIN, COLOR_RED);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + ID_FIELD).value(ID_TWO))
                .andExpect(jsonPath("$." + NAME_FIELD).value(FACULTY_NAME_SLYTHERIN))
                .andExpect(jsonPath("$." + COLOR_FIELD).value(COLOR_RED));

        verify(facultyRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        final Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, COLOR_RED);
        final JSONObject facultyJson = getJsonObjectOfFaculty(faculty);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + ID_FIELD).value(ID_ZERO))
                .andExpect(jsonPath("$." + NAME_FIELD).value(FACULTY_NAME_GRYFFINDOR))
                .andExpect(jsonPath("$." + COLOR_FIELD).value(COLOR_RED));

        verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        doNothing().when(studentRepository).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + ID_ZERO)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(facultyRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        final Faculty firstFaculty = new Faculty(ID_ONE, FACULTY_NAME_RAVENCLAW, COLOR_RED);
        final Faculty secondFaculty = new Faculty(ID_ZERO, FACULTY_NAME_HUFFLEPUFF, COLOR_GREEN);
        final Faculty thirdFaculty = new Faculty(ID_ONE, FACULTY_NAME_SLYTHERIN, COLOR_GOLD);

        List<Faculty> allFaculties = Arrays.asList(firstFaculty, secondFaculty, thirdFaculty);

        when(facultyRepository.findAll()).thenReturn(allFaculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(firstFaculty))
                .andExpect(jsonPath("$[1]").value(secondFaculty))
                .andExpect(jsonPath("$[2]").value(thirdFaculty));

        verify(facultyRepository, times(1)).findAll();
    }

    @Test
    public void testGetFacultiesByColor() throws Exception {
        final Faculty firstFaculty = new Faculty(ID_ONE, FACULTY_NAME_SLYTHERIN, COLOR_RED);

        List<Faculty> redColorFaculties = List.of(firstFaculty);

        when(facultyRepository.findByColor(anyString())).thenReturn(redColorFaculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findByColor?color=" + COLOR_RED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(firstFaculty));

        verify(facultyRepository, times(1)).findByColor(anyString());
    }

    @Test
    public void testGetFacultiesByNameOrColor() throws Exception {
        final Faculty faculty = new Faculty(ID_ONE, FACULTY_NAME_SLYTHERIN, COLOR_RED);

        List<Faculty> facultiesList = List.of(faculty);

        when(facultyRepository.findByNameOrColorAllIgnoreCase(anyString() ,anyString())).thenReturn(facultiesList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findByNameOrColor?nameOrColor=" + FACULTY_NAME_SLYTHERIN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findByNameOrColor?nameOrColor=" + COLOR_RED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(faculty));

        verify(facultyRepository, times(2)).findByNameOrColorAllIgnoreCase(anyString(), anyString());
    }

    @Test
    public void testGetStudentsByFacultyId() throws Exception {
        final Faculty faculty = new Faculty(ID_ONE, FACULTY_NAME_HUFFLEPUFF, COLOR_GREEN);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getStudents/" + ID_ONE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(facultyRepository, times(1)).findById(anyLong());
    }

    private JSONObject getJsonObjectOfFaculty(Faculty faculty) throws JSONException {
        JSONObject facultyJson = new JSONObject();

        facultyJson.put(ID_FIELD, faculty.getId());
        facultyJson.put(NAME_FIELD, faculty.getName());
        facultyJson.put(COLOR_FIELD, faculty.getColor());

        return facultyJson;
    }
}
