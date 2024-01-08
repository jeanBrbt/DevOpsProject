package org.grostarin.springboot.demorest.controllers;

import org.grostarin.springboot.demorest.exceptions.BookIdMismatchException;
import org.grostarin.springboot.demorest.exceptions.BookNotFoundException;
import org.grostarin.springboot.demorest.exceptions.BannedBookIdMismatchException;
import org.grostarin.springboot.demorest.exceptions.BannedBookNotFoundException;
import org.grostarin.springboot.demorest.exceptions.BannedBookException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public RestExceptionHandler() {
        super();
    }

    @ExceptionHandler(BannedBookNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound2(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Banned Book not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Book not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(BannedBookException.class)
    protected ResponseEntity<Object> handleNotFound3(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "livre interdit", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler({
            BookIdMismatchException.class,
            BannedBookIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex
          .getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}