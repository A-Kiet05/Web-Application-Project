package com.example.Web.Application.Project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Web.Application.Project.domain.entities.GameSession;
import java.util.List;


@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    
       List<GameSession> findByUserId(Long userId);
}