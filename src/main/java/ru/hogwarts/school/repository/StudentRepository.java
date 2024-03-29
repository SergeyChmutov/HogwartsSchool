package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);

    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    Long getAllStudentsCount();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Float getStudentsAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
