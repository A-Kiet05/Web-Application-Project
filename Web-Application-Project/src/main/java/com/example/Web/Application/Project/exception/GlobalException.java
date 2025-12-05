package com.example.Web.Application.Project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.Web.Application.Project.domain.dto.Response;



@ControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> HandleAllException (Exception ex , WebRequest webRequest){
        Response errorResponse = Response.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(ex.getMessage())
        .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> HandleNotFoundException (NotFoundException ex , WebRequest webRequest){
        Response errorResponse = Response.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .message(ex.getMessage())
        .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response> HandleAllInvalidCredentialsException (InvalidCredentialsException ex , WebRequest webRequest){
        Response errorResponse = Response.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .message(ex.getMessage())
        .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
