package com.example.restvotingapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {
            RecordAlreadyExistsException.class
            })
    public void recordAlreadyExistsException(HttpServletResponse response) throws Exception {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            })
    public void unprocessableEntityException(HttpServletResponse response) throws Exception {
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            RecordNotFoundException.class
            })
    public void notFoundException(HttpServletResponse response) throws Exception {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }


}
