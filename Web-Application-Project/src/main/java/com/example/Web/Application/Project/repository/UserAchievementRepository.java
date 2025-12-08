package com.example.Web.Application.Project.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Web.Application.Project.domain.entities.UserAchievement;
import java.util.List;


@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    
     List<UserAchievement> findByUserId(Long userId);
     boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);
}
