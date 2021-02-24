package com.rezdy.lunch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class LunchExceptionHandler extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(DateTimeParseException exception)
    {
        return new ResponseEntity<>(new ErrorResponse().setErrorMessage(exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
