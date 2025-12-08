package com.example.Web.Application.Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.LoginRequest;
import com.example.Web.Application.Project.domain.dto.RegisterRequest;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserDTO;
import com.example.Web.Application.Project.service.interf.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController{


     private final UserService userService;
     
     @PostMapping("/register")
     public ResponseEntity<Response> registryUser(@RequestBody RegisterRequest registerRequest){
            return ResponseEntity.ok(userService.registryUser(registerRequest));
     }

     @GetMapping("/login")
     public ResponseEntity<Response> getLoginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
     }
} 
