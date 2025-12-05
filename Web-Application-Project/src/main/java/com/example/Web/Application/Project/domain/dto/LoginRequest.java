package com.example.Web.Application.Project.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class LoginRequest {
    
    @NotBlank (message = "email is required!")
    private String email;

    @NotBlank (message = "password is required !")
    private String password;
}
