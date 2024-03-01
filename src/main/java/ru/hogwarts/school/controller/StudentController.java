package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(service.createStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long Id) {
        return ResponseEntity.ok(service.getStudentById(Id));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(service.updateStudent(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") Long Id) {
        service.deleteStudentById(Id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/findByAge")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(service.getStudentsByAge(age));
    }

    @GetMapping("/findByAgeBetween")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(
            @RequestParam(name = "min", required = true) Integer minAge,
            @RequestParam(name = "max", required = true) Integer maxAge
    ) {
        return ResponseEntity.ok(service.getStudentsByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/getFaculty/{id}")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable("id") Long Id) {
        final Faculty facultyOfStudent = service.getFacultyByStudentId(Id);
        return ResponseEntity.ok(service.getFacultyByStudentId(Id));
    }

    @GetMapping("/getAllStudentsCount")
    public ResponseEntity<Long> getAllStudentsCount() {
        return ResponseEntity.ok(service.getAllStudentsCount());
    }

    @GetMapping("/getStudentsAverageAge")
    public ResponseEntity<Float> getStudentsAverageAge() {
        return ResponseEntity.ok(service.getStudentsAverageAge());
    }

    @GetMapping("/getLastFiveStudents")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(service.getLastFiveStudents());
    }

    @GetMapping("/getStudentsWhoNamesBeginWithA")
    public ResponseEntity<Collection<String>> getStudentsWhoNamesBeginWithA() {
        return ResponseEntity.ok(service.getStudentsWhoNamesBeginWith("A"));
    }

    @GetMapping("/getStudentsAverageAgeByStream")
    public ResponseEntity<Double> getStudentsAverageAgeWithStream() {
        return ResponseEntity.ok(service.getStudentsAverageAgeWithStream());
    }

    @GetMapping("/print-parallel")
    public ResponseEntity<String> printStudentsParallel() {
        service.printStudentsParallel();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/print-synchronized")
    public ResponseEntity<String> printStudentsSynchronized() {
        service.printStudentsSynchronized();
        return ResponseEntity.ok().build();
    }
}
