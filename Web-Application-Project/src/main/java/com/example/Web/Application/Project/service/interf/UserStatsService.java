package com.example.Web.Application.Project.service.interf;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.entities.UserStats;

public interface UserStatsService {
    
    Response create(UserStats request);
    Response update(Long id, UserStats request);
    Response delete(Long id);
    Response getById(Long id);
    Response getAll();
}
