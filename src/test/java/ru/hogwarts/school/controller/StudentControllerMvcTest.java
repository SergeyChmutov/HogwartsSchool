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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.constants.Constants.*;

@WebMvcTest
public class StudentControllerMvcTest {
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
    private StudentController studentController;

    @Test
    public void testCreateStudent() throws Exception {
        final Student student = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);
        final JSONObject studentJson = getJsonObjectOfStudent(student);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + ID_FIELD).value(ID_ONE))
                .andExpect(jsonPath("$." + NAME_FIELD).value(STUDENT_NAME_RON))
                .andExpect(jsonPath("$." + AGE_FIELD).value(AGE_TEN));

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testGetStudent() throws Exception {
        final Student student = new Student(ID_ONE, STUDENT_NAME_GARRY, AGE_NINE);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + ID_FIELD).value(ID_ONE))
                .andExpect(jsonPath("$." + NAME_FIELD).value(STUDENT_NAME_GARRY))
                .andExpect(jsonPath("$." + AGE_FIELD).value(AGE_NINE));

        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateStudent() throws Exception {
        final Student student = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);
        final JSONObject studentJson = getJsonObjectOfStudent(student);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + ID_FIELD).value(ID_ONE))
                .andExpect(jsonPath("$." + NAME_FIELD).value(STUDENT_NAME_RON))
                .andExpect(jsonPath("$." + AGE_FIELD).value(AGE_TEN));

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        doNothing().when(studentRepository).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + ID_ZERO)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testGetAllStudents() throws Exception {
        final Student firstStudent = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);
        final Student secondStudent = new Student(ID_ZERO, STUDENT_NAME_GARRY, AGE_TWELVE);
        final Student thirdStudent = new Student(ID_ONE, STUDENT_NAME_HERMIONE, AGE_TWELVE);

        List<Student> allStudents = Arrays.asList(firstStudent, secondStudent, thirdStudent);

        when(studentRepository.findAll()).thenReturn(allStudents);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(firstStudent))
                .andExpect(jsonPath("$[1]").value(secondStudent))
                .andExpect(jsonPath("$[2]").value(thirdStudent));

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        final Student firstStudent = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);
        final Student secondStudent = new Student(ID_ZERO, STUDENT_NAME_GARRY, AGE_TWELVE);

        List<Student> tenAgeStudents = List.of(firstStudent);

        when(studentRepository.findByAge(anyInt())).thenReturn(tenAgeStudents);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/findByAge?age=" + AGE_TEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(firstStudent));

        verify(studentRepository, times(1)).findByAge(anyInt());
    }

    @Test
    public void testGetStudentsByAgeBetween() throws Exception {
        final Student firstStudent = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);
        final Student secondStudent = new Student(ID_ZERO, STUDENT_NAME_GARRY, AGE_TWELVE);

        List<Student> ageRangeStudents = List.of(firstStudent);

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(ageRangeStudents);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/findByAgeBetween?min=" + AGE_NINE + "&max=" + AGE_TEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(firstStudent));

        verify(studentRepository, times(1)).findByAgeBetween(anyInt(), anyInt());
    }

    @Test
    public void testGetFacultyByStudentId() throws Exception {
        final Student student = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getFaculty/" + ID_TWO)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentRepository, times(2)).findById(anyLong());
    }

    private JSONObject getJsonObjectOfStudent(Student student) throws JSONException {
        JSONObject studentJson = new JSONObject();

        studentJson.put(ID_FIELD, student.getId());
        studentJson.put(NAME_FIELD, student.getName());
        studentJson.put(AGE_FIELD, student.getAge());

        return studentJson;
    }
}
