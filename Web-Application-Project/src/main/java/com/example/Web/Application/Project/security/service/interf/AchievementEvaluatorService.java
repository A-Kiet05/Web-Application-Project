package com.example.Web.Application.Project.security.service.interf;

import com.example.Web.Application.Project.domain.entities.User;

public interface AchievementEvaluatorService {

    void evaluateAchievementsForUser(User user, double accuracy, double wpm);
}
