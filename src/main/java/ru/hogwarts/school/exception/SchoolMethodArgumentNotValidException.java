package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchoolMethodArgumentNotValidException extends RuntimeException {
    public SchoolMethodArgumentNotValidException(String message) {
        super(message);
    }
}
