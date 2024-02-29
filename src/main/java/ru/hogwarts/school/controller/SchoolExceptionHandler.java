package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.hogwarts.school.exception.SchoolMethodCallWithEmptyListException;
import ru.hogwarts.school.exception.SchoolMethodArgumentNotValidException;

@RestControllerAdvice
public class SchoolExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(SchoolExceptionHandler.class);

    @ExceptionHandler(SchoolMethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(SchoolMethodArgumentNotValidException exception) {
        logger.error("Method call error: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(SchoolMethodCallWithEmptyListException.class)
    public ResponseEntity<ErrorMessage> methodCallWithEmptyListException(SchoolMethodCallWithEmptyListException exception) {
        logger.error("Method call with empty list: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }
}
