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
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    
}
