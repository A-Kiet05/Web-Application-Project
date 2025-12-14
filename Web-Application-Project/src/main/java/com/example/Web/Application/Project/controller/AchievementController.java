package com.example.Web.Application.Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.AchievementDTO;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.service.interf.AchievementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {
    
    private final AchievementService achievementService;

    @PostMapping("create-achievement")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> create(@RequestBody AchievementDTO achievementDTO){

        return ResponseEntity.ok(achievementService.createAchievement(achievementDTO));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll(){

        return ResponseEntity.ok(achievementService.getAllAchievements());
    }

    @GetMapping("/get-achievement-by-id/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id){

        return ResponseEntity.ok(achievementService.getAchievementById(id));
    }

    @DeleteMapping("/delete-achievement/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable Long id){
            
        return ResponseEntity.ok(achievementService.deleteAchievement(id));

    }
}
