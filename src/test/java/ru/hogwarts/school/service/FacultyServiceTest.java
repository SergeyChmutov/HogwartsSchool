package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.SchoolMethodArgumentNotValidException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constants.Constants.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    @Mock
    private FacultyRepository repository;

    @InjectMocks
    private FacultyService out;

    // createFaculty(Faculty faculty) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateFacultyMethodWithNullParam() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createFaculty(null));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateFacultyMethodWithNullFacultyNameField() {
        Faculty faculty = new Faculty(ID_ZERO, null, COLOR_RED);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateFacultyMethodWithEmptyFacultyNameField() {
        Faculty faculty = new Faculty(ID_ZERO, EMPTY_STRING, COLOR_RED);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateFacultyMethodWithBlankFacultyNameField() {
        Faculty faculty = new Faculty(ID_ZERO, BLANK_STRING, COLOR_RED);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateFacultyMethodWithNullFacultyColorField() {
        Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, null);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateFacultyMethodWithEmptyFacultyColorField() {
        Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, EMPTY_STRING);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateFacultyMethodWithBlankFacultyColorField() {
        Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, BLANK_STRING);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnFacultyObjectWhenCallCreateFacultyMethodWithValidFacultyFields() {
        Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_SLYTHERIN, COLOR_RED);
        when(repository.save(faculty)).thenReturn(faculty);
        assertEquals(out.createFaculty(faculty), faculty);
    }

    // getFacultyById(Long id) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetFacultyByIdMethodWithNullId() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getFacultyById(null));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetFacultyByIdMethodWithNegativeId() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getFacultyById(ID_NEGATIVE));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetFacultyByIdMethodWithZeroId() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getFacultyById(ID_ZERO));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    public void shouldReturnNullWhenCallGetFacultyByIdMethodWithNotExistsId() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getFacultyById(ID_ONE));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void shouldReturnFacultyWhenCallGetFacultyByIdMethodWithExistsId() {
        final Faculty faculty = new Faculty(ID_ONE, FACULTY_NAME_HUFFLEPUFF, COLOR_GREEN);

        when(repository.save(any(Faculty.class))).thenReturn(faculty);

        final Faculty createdFaculty = out.createFaculty(faculty);

        when(repository.findById(anyLong())).thenReturn(Optional.of(faculty));

        assertEquals(out.getFacultyById(createdFaculty.getId()), createdFaculty);

        verify(repository, times(1)).save(any(Faculty.class));
        verify(repository, times(1)).findById(anyLong());
    }

    // updateFaculty(Faculty faculty) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateFacultyMethodWithNullParam() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateFaculty(null));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateFacultyMethodWithNullFacultyNameField() {
        Faculty faculty = new Faculty(ID_ZERO, null, COLOR_RED);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateFacultyMethodWithEmptyFacultyNameField() {
        Faculty faculty = new Faculty(ID_ZERO, EMPTY_STRING, COLOR_GREEN);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateFacultyMethodWithBlankFacultyNameField() {
        Faculty faculty = new Faculty(ID_ZERO, BLANK_STRING, COLOR_GREEN);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateFacultyMethodWithNullFacultyColorField() {
        Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, null);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateFacultyMethodWithEmptyFacultyColorField() {
        Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, EMPTY_STRING);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateFacultyMethodWithBlankFacultyColorField() {
        Faculty faculty = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, BLANK_STRING);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateFaculty(faculty));
        verify(repository, times(0)).save(any(Faculty.class));
    }

    @Test
    public void shouldReturnFacultyWhenCallUpdateFacultyIdMethodWithExistsId() {
        final Faculty faculty = new Faculty(ID_ONE, FACULTY_NAME_HUFFLEPUFF, COLOR_RED);

        when(repository.save(faculty)).thenReturn(faculty);

        final Faculty createdFaculty = out.createFaculty(faculty);

        when(repository.save(createdFaculty)).thenReturn(createdFaculty);

        createdFaculty.setName(FACULTY_NAME_SLYTHERIN);
        assertEquals(out.updateFaculty(createdFaculty), createdFaculty);

        createdFaculty.setColor(COLOR_GREEN);
        assertEquals(out.updateFaculty(createdFaculty), createdFaculty);

        verify(repository, times(3)).save(any(Faculty.class));
    }

    // deleteFacultyById(Long id) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallDeleteFacultyMethodWithNullId() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.deleteFacultyById(null));
        verify(repository, times(0)).deleteById(anyLong());
    }

    // getAll() method

    @Test
    public void shouldReturnEmptyCollectionWhenCallGetAllMethodWithEmptyFacultyList() {
        when(repository.findAll()).thenReturn(EMPTY_LIST);
        assertIterableEquals(out.getAll(), EMPTY_LIST);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldReturnCollectionOfCreatedFacultyWhenCallGetAllMethod() {
        final Faculty facultyHufflepuff = new Faculty(ID_ZERO, FACULTY_NAME_HUFFLEPUFF, COLOR_RED);
        final Faculty facultySlytherin = new Faculty(ID_ZERO, FACULTY_NAME_SLYTHERIN, COLOR_GOLD);
        final Faculty facultyRavenclaw = new Faculty(ID_ZERO, FACULTY_NAME_RAVENCLAW, COLOR_GREEN);
        final Faculty facultyGryffindor = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, COLOR_RED);

        when(repository.save(facultyHufflepuff)).thenReturn(facultyHufflepuff);
        when(repository.save(facultySlytherin)).thenReturn(facultySlytherin);
        when(repository.save(facultyRavenclaw)).thenReturn(facultyRavenclaw);
        when(repository.save(facultyGryffindor)).thenReturn(facultyGryffindor);

        List<Faculty> createdFaculties = new ArrayList<>();

        createdFaculties.add(out.createFaculty(facultyHufflepuff));
        createdFaculties.add(out.createFaculty(facultySlytherin));
        createdFaculties.add(out.createFaculty(facultyRavenclaw));
        createdFaculties.add(out.createFaculty(facultyGryffindor));

        when(repository.findAll()).thenReturn(createdFaculties);

        Collection<Faculty> expectedFaculties = out.getAll();

        assertIterableEquals(expectedFaculties, createdFaculties);

        verify(repository, times(4)).save(any(Faculty.class));
        verify(repository, times(1)).findAll();
    }

    // getFacultiesByColor(String color) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetFacultiesByColorMethodWithNullParam() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getFacultiesByColor(null));
        verify(repository, times(0)).findByColor(anyString());
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetFacultiesByColorMethodWithEmptyParam() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getFacultiesByColor(EMPTY_STRING));
        verify(repository, times(0)).findByColor(anyString());
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetFacultiesByColorMethodWithBlankParam() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getFacultiesByColor(BLANK_STRING));
        verify(repository, times(0)).findByColor(anyString());
    }

    @Test
    public void shouldReturnEmptyCollectionWhenCallGetFacultiesByColorMethodWithEmptyFacultiesList() {
        when(repository.findByColor(anyString())).thenReturn(EMPTY_LIST);
        assertIterableEquals(out.getFacultiesByColor(COLOR_RED), EMPTY_LIST);
        verify(repository, times(1)).findByColor(anyString());
    }

    @Test
    public void shouldReturnEmptyCollectionWhenCallGetFacultiesByColorMethodWithNoFacultiesOfColorInList() {
        final Faculty facultyRavenclaw = new Faculty(ID_ZERO, FACULTY_NAME_RAVENCLAW, COLOR_RED);
        final Faculty facultySlytherin = new Faculty(ID_ZERO, FACULTY_NAME_SLYTHERIN, COLOR_GOLD);

        when(repository.save(facultyRavenclaw)).thenReturn(facultyRavenclaw);
        when(repository.save(facultySlytherin)).thenReturn(facultySlytherin);
        when(repository.findByColor(COLOR_GREEN)).thenReturn(EMPTY_LIST);

        out.createFaculty(facultyRavenclaw);
        out.createFaculty(facultySlytherin);

        assertIterableEquals(out.getFacultiesByColor(COLOR_GREEN), EMPTY_LIST);

        verify(repository, times(2)).save(any(Faculty.class));
        verify(repository, times(1)).findByColor(anyString());
    }

    @Test
    public void shouldReturnCollectionOfFacultiesWhenCallGetFacultiesByColorMethodWithFacultiesOfTheColorInList() {
        Collection<Faculty> createdFaculties = new ArrayList<>();
        final Faculty facultyHufflepuff = new Faculty(ID_ONE, FACULTY_NAME_HUFFLEPUFF, COLOR_RED);
        final Faculty facultySlytherin = new Faculty(ID_TWO, FACULTY_NAME_SLYTHERIN, COLOR_RED);
        final Faculty facultyGriffindor = new Faculty(ID_ZERO, FACULTY_NAME_GRYFFINDOR, COLOR_GOLD);

        when(repository.save(facultyHufflepuff)).thenReturn(facultyHufflepuff);
        when(repository.save(facultySlytherin)).thenReturn(facultySlytherin);
        when(repository.save(facultyGriffindor)).thenReturn(facultyGriffindor);
        when(repository.findByColor(COLOR_RED)).thenReturn(List.of(facultyHufflepuff, facultySlytherin));

        createdFaculties.add(out.createFaculty(facultyHufflepuff));
        createdFaculties.add(out.createFaculty(facultySlytherin));
        out.createFaculty(facultyGriffindor);

        Collection<Faculty> expectedFaculties = out.getFacultiesByColor(COLOR_RED);

        assertIterableEquals(expectedFaculties, createdFaculties);

        verify(repository, times(3)).save(any(Faculty.class));
        verify(repository, times(1)).findByColor(anyString());
    }
}
