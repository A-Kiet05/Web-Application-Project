package com.example.Web.Application.Project.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.ScoreDTO;
import com.example.Web.Application.Project.service.interf.ScoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;

    @PostMapping("/post-score")
    public ResponseEntity<Response> postScore(@RequestBody ScoreDTO scoreDTO){
        return ResponseEntity.ok(scoreService.postScore(scoreDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllScore(){
        return ResponseEntity.ok(scoreService.getAllScore());
    }

    @GetMapping("/leader-board")
    public ResponseEntity<Response> leaderBoard(
            @RequestParam(defaultValue = "10" , required = true) int top
    ) {
       
        Pageable pageable = PageRequest.of(0, top);
        return ResponseEntity.ok(scoreService.leaderBoard(top, pageable));
    }

}
