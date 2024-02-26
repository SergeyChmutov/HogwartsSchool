package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.SchoolMethodArgumentNotValidException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository repository;
    private Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        logger.debug("Was invoked method for create faculty: {}", faculty);
        checkFacultyFieldsValidValue(faculty);
        return repository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method for get faculty by id");
        logger.debug("Was invoked method for get faculty by id: {}", id);
        if (id == null || id <= 0) {
            throw new SchoolMethodArgumentNotValidException(
                    "Идентификатор факультета должен быть указан и иметь положительное значение"
            );
        }
        Optional<Faculty> faculty = repository.findById(id);
        return faculty.orElseThrow(
                () -> new SchoolMethodArgumentNotValidException("Факультет с указанным идентификатором не найден")
        );
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        logger.debug("Was invoked method for update faculty: {}", faculty);
        checkFacultyFieldsValidValue(faculty);
        return repository.save(faculty);
    }

    public void deleteFacultyById(Long id) {
        logger.info("Was invoked method for delete faculty by id");
        logger.debug("Was invoked method for delete faculty by id: {}", id);
        if (id == null) {
            throw new SchoolMethodArgumentNotValidException("Идентификатор факультета не может иметь значение null");
        }
        repository.deleteById(id);
    }

    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for get all faculties");
        return repository.findAll();
    }

    public Collection<Faculty> getFacultiesByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for get faculties by name or color");
        logger.debug("Was invoked method for get faculties by name or color: {}", nameOrColor);
        if (nameOrColor == null) {
            throw new SchoolMethodArgumentNotValidException(
                    "Строка для поиска имени или цвета факультета не может иметь значение null"
            );
        }
        return repository.findByNameOrColorAllIgnoreCase(nameOrColor, nameOrColor);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method for get faculties by color");
        logger.debug("Was invoked method for get faculties by color: {}", color);
        if (color == null || color.isBlank() || color.isEmpty()) {
            throw new SchoolMethodArgumentNotValidException(
                    "Цвет факультета должен быть указан и иметь не пустое значение"
            );
        }

        return repository.findByColor(color);
    }

    public Collection<Student> getStudentsByFacultyId(Long Id) {
        logger.info("Was invoked method for get students by faculty id");
        logger.debug("Was invoked method for get students by faculty id: {}", Id);
        final Faculty findFaculty = getFacultyById(Id);
        return findFaculty.getStudents();
    }

    private void checkFacultyFieldsValidValue(Faculty faculty) {
        if (faculty == null) {
            throw new SchoolMethodArgumentNotValidException("Факультет не может иметь значение null");
        }
        final String facultyName = faculty.getName();
        if (facultyName == null || facultyName.isEmpty() || facultyName.isBlank()) {
            throw new SchoolMethodArgumentNotValidException("Название факультета не указано или пустое");
        }
        final String facultyColor = faculty.getColor();
        if (facultyColor == null || facultyColor.isEmpty() || facultyColor.isBlank()) {
            throw new SchoolMethodArgumentNotValidException("Цвет факультета не указан или пуст");
        }
    }
}
