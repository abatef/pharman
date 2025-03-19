package com.abatef.pharman.exceptions.handlers;

import com.abatef.pharman.exceptions.NonExistingEmpIdException;
import com.abatef.pharman.exceptions.NonExistingUsernameException;
import com.abatef.pharman.exceptions.NonUniqueUsernameException;
import com.abatef.pharman.models.employee.Employee;
import jakarta.persistence.ElementCollection;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class MainExceptionHandler {

    @Builder
    public static class ErrorResponse {
        public String message;
        public String details;
    }

    @ExceptionHandler(NonExistingUsernameException.class)
    public ResponseEntity<ErrorResponse> NonExistingUsernameException(NonExistingUsernameException e) {
        ErrorResponse msg = ErrorResponse.builder()
                .message(e.getMessage()).details(e.getUsername()).build();
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NonExistingEmpIdException.class)
    public ResponseEntity<ErrorResponse> NonExistingEmpIdException(NonExistingEmpIdException e) {
        ErrorResponse msg = ErrorResponse.builder()
                .message(e.getMessage()).details(e.getEmpId().toString()).build();
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NonUniqueUsernameException.class)
    public ResponseEntity<ErrorResponse> NonUniqueUsernameException(NonUniqueUsernameException e) {
        ErrorResponse msg = ErrorResponse.builder()
                .message(e.getMessage()).details(e.getUsername()).build();
        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }
}
