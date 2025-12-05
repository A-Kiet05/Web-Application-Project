package com.example.Web.Application.Project.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Web.Application.Project.domain.entities.Score;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {


       @Query("SELECT s FROM Score s ORDER BY s.score DESC")
       List<Score> findTopScores(Pageable pageable);
}
