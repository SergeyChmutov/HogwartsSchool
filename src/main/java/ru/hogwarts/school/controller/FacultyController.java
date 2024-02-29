package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(service.createFaculty(faculty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable("id") Long Id) {
        final Faculty faculty = service.getFacultyById(Id);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        final Faculty updatedFaculty = service.updateFaculty(faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFaculty(@PathVariable("id") Long Id) {
        service.deleteFacultyById(Id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/findByColor")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@RequestParam String color) {
        return ResponseEntity.ok(service.getFacultiesByColor(color));
    }

    @GetMapping("/findByNameOrColor")
    public ResponseEntity<Collection<Faculty>> getFacultiesByNameOrColor(@RequestParam String nameOrColor) {
        return ResponseEntity.ok(service.getFacultiesByNameOrColor(nameOrColor));
    }

    @GetMapping("/getStudents/{id}")
    public ResponseEntity<Collection<Student>> getStudentsByFacultyId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getStudentsByFacultyId(id));
    }

    @GetMapping("/getFacultyWithLongestName")
    public ResponseEntity<String> getFacultyWithVeryLongestName() {
        return ResponseEntity.ok(service.getFacultyWithLongestName());
    }
}
