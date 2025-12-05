package com.example.Web.Application.Project.service.interf;

import org.springframework.data.domain.Pageable;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.ScoreDTO;

public interface ScoreService {
    
    Response postScore(ScoreDTO scoreDTO);
    Response getAllScore();
    Response leaderBoard(int top ,Pageable pageable );
}
