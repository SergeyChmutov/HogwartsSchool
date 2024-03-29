package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.exception.SchoolMethodArgumentNotValidException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repository;
    private Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        logger.debug("Was invoked method for create student: {}", student);
        checkStudentFieldsValidValue(student);
        return repository.save(student);
    }

    public Student getStudentById(Long id) {
        logger.info("Was invoked method for get student by id");
        logger.debug("Was invoked method for get student by id: {}", id);
        if (id == null || id <= 0) {
            throw new SchoolMethodArgumentNotValidException(
                    "Идентификатор студента должен быть указан и иметь положительное значение"
            );
        }
        Optional<Student> student = repository.findById(id);
        return student.orElseThrow(
                () -> new SchoolMethodArgumentNotValidException("Студент с указанным идентификатором не найден")
        );

    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        logger.debug("Was invoked method for update student: {}", student);
        checkStudentFieldsValidValue(student);
        return repository.save(student);
    }

    public void deleteStudentById(Long id) {
        logger.info("Was invoked method for delete student by id");
        logger.debug("Was invoked method for delete student by id: {}", id);
        if (id == null) {
            throw new SchoolMethodArgumentNotValidException("Идентификатор студента не может иметь значение null");
        }
        repository.deleteById(id);
    }

    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return repository.findAll();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        logger.info("Was invoked method for get students by age");
        logger.debug("Was invoked method for get students by age: {}", age);
        if (age == null || age <= 0) {
            throw new SchoolMethodArgumentNotValidException(
                    "Возраст студентов должен быть указан и иметь положительное значение"
            );
        }

        return repository.findByAge(age);
    }

    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method for get students by age between");
        logger.debug("Was invoked method for get students by age between: {} and: {}", minAge, maxAge);
        if (minAge == null || maxAge == null) {
            throw new SchoolMethodArgumentNotValidException(
                    "Возраст студентов должен быть указан"
            );
        }
        return repository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyByStudentId(Long Id) {
        logger.info("Was invoked method for get faculty by student id");
        logger.debug("Was invoked method for get faculty by student id: {}", Id);
        final Student findStudent = getStudentById(Id);
        return findStudent.getFaculty();
    }

    public Long getAllStudentsCount() {
        logger.info("Was invoked method for get all students count");
        return repository.getAllStudentsCount();
    }

    public Float getStudentsAverageAge() {
        logger.info("Was invoked method for get students average age");
        return repository.getStudentsAverageAge();
    }

    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last 5 students");
        return repository.getLastFiveStudents();
    }

    private void checkStudentFieldsValidValue(Student student) {
        if (student == null) {
            throw new SchoolMethodArgumentNotValidException("Студент не может иметь значение null");
        }
        final String studentName = student.getName();
        if (studentName == null || studentName.isEmpty() || studentName.isBlank()) {
            throw new SchoolMethodArgumentNotValidException("Имя студента не указано или пустое");
        }
        if (student.getAge() <= 0) {
            throw new SchoolMethodArgumentNotValidException("Возраст студента должен быть задан положительным числом");
        }
    }
}
