package com.example.Web.Application.Project.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message){
         super(message);
    }
}
