package com.example.exception;

public class StudentAlreadyPresentException extends RuntimeException {
    public StudentAlreadyPresentException(String message) {
        super(message);
    }
}