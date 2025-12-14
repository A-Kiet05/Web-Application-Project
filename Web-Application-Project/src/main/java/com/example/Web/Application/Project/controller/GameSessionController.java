package com.example.Web.Application.Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.SessionRequest;
import com.example.Web.Application.Project.service.interf.GameSessionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/game-session")
@RequiredArgsConstructor
public class GameSessionController {
    
    private final GameSessionService gameSessionService;

    @PostMapping("/save-session")
     @PreAuthorize("hasAuthority('ADMIN')")
     public ResponseEntity<Response> saveSession(@RequestBody SessionRequest sessionRequest){

        return ResponseEntity.ok(gameSessionService.saveSession(sessionRequest));
     }

    @GetMapping("/get-session-by-id/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getSessionById(@PathVariable Long id){

        return ResponseEntity.ok(gameSessionService.getSessionsByUser(id));
    }

    @GetMapping("/get-own-session")
    public ResponseEntity<Response> getOwnSession(){
        return ResponseEntity.ok(gameSessionService.getYourOwnSession());
    }

}
