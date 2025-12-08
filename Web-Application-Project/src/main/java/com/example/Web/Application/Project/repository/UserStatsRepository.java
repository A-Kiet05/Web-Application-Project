package com.example.Web.Application.Project.repository;

import org.springframework.stereotype.Repository;

import com.example.Web.Application.Project.domain.entities.UserStats;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStats, Long>  {
    
}
