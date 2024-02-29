package ru.hogwarts.school.exception;

public class SchoolMethodCallWithEmptyListException extends RuntimeException {
    public SchoolMethodCallWithEmptyListException(String message) {
        super(message);
    }
}
