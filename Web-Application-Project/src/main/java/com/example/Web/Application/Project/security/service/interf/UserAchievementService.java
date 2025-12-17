package com.example.Web.Application.Project.security.service.interf;



import com.example.Web.Application.Project.domain.dto.Response;


import java.util.List;

public interface UserAchievementService {

    

    Response delete(Long id);

    Response getById(Long id);

    Response getAllUserAchievement();
    Response getYourOwnAchievement();
}
