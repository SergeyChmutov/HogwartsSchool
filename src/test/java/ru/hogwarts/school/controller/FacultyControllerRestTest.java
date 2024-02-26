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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constants.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class FacultyControllerRestTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateFaculty() throws Exception {
        final Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, COLOR_RED);

        final Faculty createdFaculty = restTemplate.postForObject(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );

        assertNotNull(createdFaculty);
        assertEquals(createdFaculty.getName(), FACULTY_NAME_GRYFFINDOR);
        assertEquals(createdFaculty.getColor(), COLOR_RED);
        assertNotNull(createdFaculty.getId());
    }

    @Test
    public void testGetFaculty() throws Exception {
        final Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_SLYTHERIN, COLOR_RED);

        final Faculty createdFaculty = restTemplate.postForObject(
                "http://localhost:" + port + "/facilty",
                faculty,
                Faculty.class
        );

        final Faculty foundFaculty = restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/" + createdFaculty.getId(),
                Faculty.class
        );

        assertNotNull(foundFaculty);
        assertEquals(foundFaculty, createdFaculty);
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        final Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_SLYTHERIN, COLOR_RED);

        final Faculty createdFaculty = restTemplate.postForObject(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );

        createdFaculty.setName(FACULTY_NAME_RAVENCLAW);
        createdFaculty.setColor(COLOR_GREEN);

        final Faculty updatedFaculty = restTemplate.postForObject(
                "http://localhost:" + port + "/faculty",
                createdFaculty,
                Faculty.class
        );

        assertNotNull(updatedFaculty);
        assertEquals(updatedFaculty, createdFaculty);
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        final Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, COLOR_GOLD);

        final Faculty createdFaculty = restTemplate.postForObject(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + createdFaculty.getId(),
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty",
                List.class
        );

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void testGetFacultiesByColor() throws Exception {
        ResponseEntity<List> responseEntityRedColor = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/findByColor?color=" + COLOR_RED,
                List.class
        );

        assertEquals(responseEntityRedColor.getStatusCode(), HttpStatus.OK);
        assertFalse(responseEntityRedColor.getBody().isEmpty());

        ResponseEntity<List> responseEntityGoldColor = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/findByColor?color=" + COLOR_GOLD,
                List.class
        );

        assertEquals(responseEntityGoldColor.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntityGoldColor.getBody().isEmpty());
    }

    @Test
    public void testGetFacultiesByNameOrColor() throws Exception {
        ResponseEntity<List> responseEntityRedColor = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/findByNameOrColor?nameOrColor=" + COLOR_RED,
                List.class
        );

        assertEquals(responseEntityRedColor.getStatusCode(), HttpStatus.OK);
        assertFalse(responseEntityRedColor.getBody().isEmpty());

        ResponseEntity<List> responseEntityGoldColor = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/findByNameOrColor?nameOrColor=" + FACULTY_NAME_SLYTHERIN,
                List.class
        );

        assertEquals(responseEntityGoldColor.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntityGoldColor.getBody().isEmpty());
    }

    @Test
    public void testGetStudentsByFacultyId() throws Exception {
        final Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_HUFFLEPUFF, COLOR_GREEN);

        final Faculty createdFaculty = restTemplate.postForObject(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/getStudents/" + createdFaculty.getId(),
                List.class
        );

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.getBody().isEmpty());
    }
}
