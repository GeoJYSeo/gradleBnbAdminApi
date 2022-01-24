package com.example.gradlebnbadminapi.exception.handler;

import com.example.gradlebnbadminapi.exception.EmailDuplicatedException;
import com.example.gradlebnbadminapi.exception.EmailNotExistedException;
import com.example.gradlebnbadminapi.exception.UnauthenticatedException;
import com.example.gradlebnbadminapi.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDuplicatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerEmailDuplicatedException(EmailDuplicatedException ex) {
        return ErrorResponse.of(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(EmailNotExistedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerEmailNotExistException(EmailNotExistedException ex) {
        return ErrorResponse.of(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handlerUnauthenticatedException(UnauthenticatedException ex) {
        return ErrorResponse.of(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_GATEWAY)
//    public ErrorResponse handlerException(Exception ex) {
//        return ErrorResponse.of(HttpStatus.BAD_GATEWAY, ex.getMessage());
//    }
}
