package com.project.patientmanagementservice.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String invalidEmailIdProvided) {
        super(invalidEmailIdProvided);
    }
}
