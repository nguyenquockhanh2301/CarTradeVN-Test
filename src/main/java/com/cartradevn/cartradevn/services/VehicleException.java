package com.cartradevn.cartradevn.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class VehicleException extends RuntimeException {
    public VehicleException(String string) {
        // TODO Auto-generated constructor stub
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
        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

        @ExceptionHandler(VehicleException.class)
        public ResponseEntity<String> handleVehicleException(VehicleException e) {
            log.error("Vehicle exception occurred: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleGeneralException(Exception e) {
            log.error("Unexpected error", e);
            return new ResponseEntity<>(
                    "Internal server error: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
