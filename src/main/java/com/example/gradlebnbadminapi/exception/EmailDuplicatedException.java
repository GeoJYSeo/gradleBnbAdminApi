package com.example.gradlebnbadminapi.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailDuplicatedException extends RuntimeException {

    public static final String MESSAGE = "Already Registered Email.";

    public EmailDuplicatedException(String userEmail) {
        super(MESSAGE);
        log.error(MESSAGE + ": " + userEmail);
    }
}
