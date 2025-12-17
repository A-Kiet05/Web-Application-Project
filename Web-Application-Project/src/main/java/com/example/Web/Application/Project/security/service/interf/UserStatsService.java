package com.example.Web.Application.Project.security.service.interf;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserStatsRequest;
import com.example.Web.Application.Project.domain.entities.UserStats;

public interface UserStatsService {
    
    Response create(UserStatsRequest request);
    Response update(Long id);
    Response delete(Long id);
    Response getById(Long id);
    Response getAll();
    Response getYourOwnStats();
}
