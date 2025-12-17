package com.example.Web.Application.Project.service.interf;

import com.example.Web.Application.Project.domain.dto.AchievementDTO;
import com.example.Web.Application.Project.domain.dto.Response;

public interface AchievementService {
    
    Response createAchievement(AchievementDTO dto);

    Response getAllAchievements();

    Response getAchievementById(Long id);

    Response deleteAchievement(Long id);
}

