package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constants.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class StudentControllerRestTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateStudent() throws Exception {
        final Student student = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_TEN);

        final Student createdStudent = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        assertNotNull(createdStudent);
        assertEquals(createdStudent.getName(), STUDENT_NAME_RON);
        assertEquals(createdStudent.getAge(), AGE_TEN);
        assertNotEquals(createdStudent.getId(), ID_ZERO);
    }

    @Test
    public void testGetStudent() throws Exception {
        final Student student = new Student(ID_ZERO, STUDENT_NAME_GARRY, AGE_TEN);

        final Student createdStudent = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        final Student foundStudent = restTemplate.getForObject(
                "http://localhost:" + port + "/student/" + createdStudent.getId(),
                Student.class
        );

        assertNotNull(foundStudent);
        assertEquals(foundStudent, createdStudent);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        final Student student = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_TEN);

        final Student createdStudent = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        createdStudent.setAge(AGE_TWELVE);
        createdStudent.setName(STUDENT_NAME_HERMIONE);

        final Student updatedStudent = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                createdStudent,
                Student.class
        );

        assertNotNull(updatedStudent);
        assertEquals(updatedStudent, createdStudent);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        final Student student = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_TEN);

        final Student createdStudent = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + createdStudent.getId(),
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetAllStudents() throws Exception {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student",
                List.class
        );

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        ResponseEntity<List> responseEntityTenAge = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/findByAge?age=" + AGE_TEN,
                List.class
        );

        assertEquals(responseEntityTenAge.getStatusCode(), HttpStatus.OK);
        assertFalse(responseEntityTenAge.getBody().isEmpty());

        ResponseEntity<List> responseEntityNineAge = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/findByAge?age=" + AGE_NINE,
                List.class
        );

        assertEquals(responseEntityNineAge.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntityNineAge.getBody().isEmpty());
    }

    @Test
    public void testGetStudentsByAgeBetween() throws Exception {
        ResponseEntity<List> responseEntityBetweenNineAndTenAge = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/findByAgeBetween?min=" + AGE_NINE + "&max=" + AGE_TEN,
                List.class
        );

        assertEquals(responseEntityBetweenNineAndTenAge.getStatusCode(), HttpStatus.OK);
        assertFalse(responseEntityBetweenNineAndTenAge.getBody().isEmpty());

        ResponseEntity<List> responseEntityBetweenTenAndTwelveAge = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/findByAgeBetween?min=" + AGE_TEN + "&max=" + AGE_TWELVE,
                List.class
        );

        assertEquals(responseEntityBetweenTenAndTwelveAge.getStatusCode(), HttpStatus.OK);
        assertFalse(responseEntityBetweenTenAndTwelveAge.getBody().isEmpty());
    }

    @Test
    public void testGetFacultyByStudentId() throws Exception {
        final Student student = new Student(ID_ZERO, STUDENT_NAME_GARRY, AGE_THIRTEEN);

        final Student createdStudent = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        final Faculty foundFaculty = restTemplate.getForObject(
                "http://localhost:" + port + "/student/getFaculty/" + createdStudent.getId(),
                Faculty.class
        );

        assertNull(foundFaculty);
    }
}
