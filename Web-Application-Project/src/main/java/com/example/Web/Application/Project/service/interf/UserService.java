package com.example.Web.Application.Project.service.interf;

import com.example.Web.Application.Project.domain.dto.LoginRequest;
import com.example.Web.Application.Project.domain.dto.RegisterRequest;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserDTO;
import com.example.Web.Application.Project.domain.entities.User;

public interface UserService {
    
    Response registryUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);

    Response getAllUser();
    User getLogin();
   
}
