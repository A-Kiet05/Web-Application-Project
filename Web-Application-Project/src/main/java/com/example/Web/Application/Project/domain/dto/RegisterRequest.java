package com.example.Web.Application.Project.domain.dto;

import com.example.Web.Application.Project.domain.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest {

    @NotBlank(message = "username must not be null")
    private String username;

    @NotBlank(message = "password not be null")
    private String password;

    @NotBlank(message = "email not null")
    private String email;

    @NotBlank(message = "full name require")
    private String fullName;

    private UserRole role;

    
}
