package com.example.Web.Application.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Web.Application.Project.domain.entities.Achievement;
import java.util.Optional;


@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    
           Optional<Achievement> findByCode(String code);
}
