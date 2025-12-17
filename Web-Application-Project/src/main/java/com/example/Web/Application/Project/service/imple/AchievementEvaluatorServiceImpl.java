package com.example.Web.Application.Project.service.imple;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.entities.Achievement;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.entities.UserAchievement;
import com.example.Web.Application.Project.repository.AchievementRepository;
import com.example.Web.Application.Project.repository.UserAchievementRepository;
import com.example.Web.Application.Project.service.interf.AchievementEvaluatorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementEvaluatorServiceImpl implements AchievementEvaluatorService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;

    @Override
    public void evaluateAchievementsForUser(User user, double accuracy, double wpm) {

        List<Achievement> achievements = achievementRepository.findAll();

        for (Achievement achievement : achievements) {

            // Example condition: ACCURACY_100 or WPM_50
            String condition = achievement.getConditionType();

            boolean achieved = false;

            if (condition.startsWith("ACCURACY_")) {
                double required = Double.parseDouble(condition.split("_")[1]);
                if (accuracy >= required) achieved = true;
            }

            if (condition.startsWith("WPM_")) {
                double required = Double.parseDouble(condition.split("_")[1]);
                if (wpm >= required) achieved = true;
            }

            if (achieved) {
                boolean exists = userAchievementRepository
                        .existsByUserIdAndAchievementId(user.getId(), achievement.getId());

                if (!exists) {
                    UserAchievement userAchievement = new UserAchievement();
                    userAchievement.setUser(user);
                    userAchievement.setAchievement(achievement);
                    userAchievementRepository.save(userAchievement);
                }
            }
        }
    }
}

