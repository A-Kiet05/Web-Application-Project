package com.example.Web.Application.Project.service.interf;


import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.SessionRequest;
import com.example.Web.Application.Project.domain.dto.SessionResponse;

public interface GameSessionService {

    Response saveSession(SessionRequest sessionRequest);
    Response getSessionsByUser(Long userId);
    Response getYourOwnSession();
}
