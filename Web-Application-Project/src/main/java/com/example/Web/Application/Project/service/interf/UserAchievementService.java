package com.example.Web.Application.Project.service.interf;



import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserAchievementRequest;

import java.util.List;

public interface UserAchievementService {

    Response create(UserAchievementRequest request);

    Response update(Long id, UserAchievementRequest request);

    Response delete(Long id);

    Response getById(Long id);

    Response getAllUserAchievement();
}
