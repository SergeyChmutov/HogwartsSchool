package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.SchoolMethodArgumentNotValidException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constants.Constants.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService out;

    // createStudent(Student student) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateStudentMethodWithNullParam() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createStudent(null));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateStudentMethodWithNullStudentNameField() {
        Student student = new Student(ID_ZERO, null, AGE_TEN);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateStudentMethodWithEmptyStudentNameField() {
        Student student = new Student(ID_ZERO, EMPTY_STRING, AGE_TEN);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateStudentMethodWithBlankStudentNameField() {
        Student student = new Student(ID_ZERO, BLANK_STRING, AGE_TEN);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateStudentMethodWithNegativeStudentAgeField() {
        Student student = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_NEGATIVE);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallCreateStudentMethodWithZeroStudentAgeField() {
        Student student = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_ZERO);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.createStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnStudentObjectWhenCallCreateStudentMethodWithValidStudentFields() {
        final Student student = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);
        when(repository.save(student)).thenReturn(student);
        assertEquals(out.createStudent(student), student);
    }

    // getStudentById(Long id) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetStudentByIdMethodWithNullId() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getStudentById(null));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetStudentByIdMethodWithNegativeId() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getStudentById(ID_NEGATIVE));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetStudentByIdMethodWithZeroId() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getStudentById(ID_ZERO));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    public void shouldReturnNullWhenCallGetStudentByIdMethodWithNotExistsId() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getStudentById(ID_ONE));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void shouldReturnStudentWhenCallGetStudentByIdMethodWithExistsId() {
        final Student student = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);

        when(repository.save(any(Student.class))).thenReturn(student);

        final Student createdStudent = out.createStudent(student);

        when(repository.findById(anyLong())).thenReturn(Optional.of(student));

        assertEquals(out.getStudentById(createdStudent.getId()), student);

        verify(repository, times(1)).save(any(Student.class));
        verify(repository, times(1)).findById(anyLong());
    }

    // updateStudent(Student student) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateStudentMethodWithNullParam() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateStudent(null));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateStudentMethodWithNullStudentNameField() {
        Student student = new Student(ID_ZERO, null, AGE_TEN);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateStudentMethodWithEmptyStudentNameField() {
        Student student = new Student(ID_ZERO, EMPTY_STRING, AGE_TEN);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateStudentMethodWithBlankStudentNameField() {
        Student student = new Student(ID_ZERO, BLANK_STRING, AGE_TEN);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateStudentMethodWithNegativeStudentAgeField() {
        Student student = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_NEGATIVE);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateStudentMethodWithZeroStudentAgeField() {
        Student student = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_ZERO);
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.updateStudent(student));
        verify(repository, times(0)).save(any(Student.class));
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallUpdateStudentIdMethodWithNotExistsId() {
        when(repository.save(STUDENT_RON)).thenReturn(STUDENT_RON);
        assertEquals(STUDENT_RON, out.updateStudent(STUDENT_RON));
        verify(repository, times(1)).save(any(Student.class));
    }

    @Test
    public void shouldReturnStudentWhenCallUpdateStudentIdMethodWithExistsId() {
        final Student student = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);

        when(repository.save(student)).thenReturn(student);

        final Student createdStudent = out.createStudent(student);

        when(repository.save(createdStudent)).thenReturn(createdStudent);

        createdStudent.setAge(AGE_TWELVE);
        assertEquals(out.updateStudent(createdStudent), createdStudent);

        createdStudent.setName(STUDENT_NAME_HERMIONE);
        assertEquals(out.updateStudent(createdStudent), createdStudent);

        verify(repository, times(3)).save(any(Student.class));

    }

    // deleteStudentById(Long id) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallDeleteStudentMethodWithNullId() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.deleteStudentById(null));
        verify(repository, times(0)).deleteById(anyLong());
    }

    // getAll() method

    @Test
    public void shouldReturnEmptyCollectionWhenCallGetAllMethodWithEmptyStudentList() {
        when(repository.findAll()).thenReturn(EMPTY_LIST);
        assertIterableEquals(out.getAll(), EMPTY_LIST);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldReturnCollectionOfCreatedStudentWhenCallGetAllMethod() {
        final Student studentRon = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_TEN);
        final Student studentGarry = new Student(ID_ZERO, STUDENT_NAME_GARRY, AGE_TEN);
        final Student studentHermione = new Student(ID_ZERO, STUDENT_NAME_HERMIONE, AGE_TWELVE);

        when(repository.save(studentRon)).thenReturn(studentRon);
        when(repository.save(studentGarry)).thenReturn(studentGarry);
        when(repository.save(studentHermione)).thenReturn(studentHermione);

        List<Student> createdStudents = new ArrayList<>();

        createdStudents.add(out.createStudent(studentRon));
        createdStudents.add(out.createStudent(studentHermione));
        createdStudents.add(out.createStudent(studentGarry));

        when(repository.findAll()).thenReturn(createdStudents);

        Collection<Student> expectedStudents = out.getAll();

        assertIterableEquals(expectedStudents, expectedStudents);

        verify(repository, times(3)).save(any(Student.class));
        verify(repository, times(1)).findAll();
    }

    // getStudentsByAge(Integer age) method

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetStudentsByAgeMethodWithNullAge() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getStudentsByAge(null));
        verify(repository, times(0)).findByAge(anyInt());
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetStudentsByAgeMethodWithNegativeAge() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getStudentsByAge(AGE_NEGATIVE));
        verify(repository, times(0)).findByAge(anyInt());
    }

    @Test
    public void shouldReturnSchoolMethodArgumentNotValidExceptionWhenCallGetStudentsByAgeMethodWithZeroAge() {
        assertThrows(SchoolMethodArgumentNotValidException.class, () -> out.getStudentsByAge(AGE_ZERO));
        verify(repository, times(0)).findByAge(anyInt());
    }

    @Test
    public void shouldReturnEmptyCollectionWhenCallGetStudentsByAgeMethodWithEmptyStudentList() {
        when(repository.findByAge(anyInt())).thenReturn(EMPTY_LIST);
        assertIterableEquals(out.getStudentsByAge(AGE_TEN), EMPTY_LIST);
        verify(repository, times(1)).findByAge(anyInt());
    }

    @Test
    public void shouldReturnEmptyCollectionWhenCallGetStudentsByAgeMethodWithNoStudentsOfAgeInList() {
        final Student studentGarry = new Student(ID_ZERO, STUDENT_NAME_GARRY, AGE_TEN);
        final Student studentHermione = new Student(ID_ZERO, STUDENT_NAME_HERMIONE, AGE_TEN);

        when(repository.save(studentGarry)).thenReturn(studentGarry);
        when(repository.save(studentHermione)).thenReturn(studentHermione);
        when(repository.findByAge(AGE_TWELVE)).thenReturn(EMPTY_LIST);

        out.createStudent(studentHermione);
        out.createStudent(studentGarry);

        assertIterableEquals(out.getStudentsByAge(AGE_TWELVE), EMPTY_LIST);

        verify(repository, times(2)).save(any(Student.class));
        verify(repository, times(1)).findByAge(anyInt());
    }

    @Test
    public void shouldReturnCollectionOfStudentsWhenCallGetStudentsByAgeMethodWithStudentsOfTheAgeInList() {
        Collection<Student> createdStudents = new ArrayList<>();
        final Student studentRon = new Student(ID_ZERO, STUDENT_NAME_RON, AGE_TEN);
        final Student studentGarry = new Student(ID_ZERO, STUDENT_NAME_GARRY, AGE_TEN);
        final Student studentHermione = new Student(ID_ZERO, STUDENT_NAME_HERMIONE, AGE_TWELVE);

        when(repository.save(studentRon)).thenReturn(studentRon);
        when(repository.save(studentGarry)).thenReturn(studentGarry);
        when(repository.findByAge(AGE_TEN)).thenReturn(List.of(studentRon, studentGarry));

        createdStudents.add(out.createStudent(studentRon));
        createdStudents.add(out.createStudent(studentGarry));
        out.createStudent(studentHermione);

        Collection<Student> expectedStudents = out.getStudentsByAge(AGE_TEN);

        assertIterableEquals(expectedStudents, createdStudents);
        verify(repository, times(3)).save(any(Student.class));
        verify(repository, times(1)).findByAge(anyInt());
    }
}
