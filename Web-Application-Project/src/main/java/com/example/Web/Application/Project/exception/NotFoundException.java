package com.example.Web.Application.Project.exception;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message){
        super(message);
    }
}