package com.example.Web.Application.Project.repository;

import org.springframework.stereotype.Repository;

import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.entities.UserStats;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStats, Long>  {
    
    Optional<UserStats> findByUserId(Long userId);
}
