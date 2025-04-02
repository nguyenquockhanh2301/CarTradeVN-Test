package com.cartradevn.cartradevn.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class VehicleException extends RuntimeException {
     public VehicleException(String string) {
        //TODO Auto-generated constructor stub
    }

    // Base Exception
    public static class BaseException extends RuntimeException {
        public BaseException(String message) {
            super(message);
        }
    }

    // Validation Exception
    public static class ValidationException extends BaseException {
        public ValidationException(String message) {
            super(message);
        }
    }

    // Not Found Exception
    public static class NotFoundException extends BaseException {
        public NotFoundException(String message) {
            super(message);
        }
    }

    // Business Logic Exception
    public static class BusinessException extends BaseException {
        public BusinessException(String message) {
            super(message);
        }
    }

    // Global Exception Handler
    @RestControllerAdvice
    public static class GlobalExceptionHandler {
        
        @ExceptionHandler(ValidationException.class)
        public ResponseEntity<String> handleValidationException(ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<String> handleBusinessException(BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleGeneralException(Exception e) {
            return new ResponseEntity<>("Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
