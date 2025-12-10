package com.example.Web.Application.Project.service.interf;

import org.springframework.data.domain.Pageable;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.ScoreRequest;
import com.example.Web.Application.Project.domain.dto.ScoreResponse;

public interface ScoreService {
    
    Response postScore(ScoreRequest scoreRequest);
    Response getAllScore();
    Response leaderBoard(int top ,Pageable pageable );
}
