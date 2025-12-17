package com.example.Web.Application.Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.service.interf.UserAchievementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-achievement")
@RequiredArgsConstructor
public class UserAchievementController {
    
    private final UserAchievementService userAchievementService;

    @DeleteMapping("/delete-user-achievement/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id){
        return ResponseEntity.ok(userAchievementService.delete(id));
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getById(@PathVariable Long id){
        return ResponseEntity.ok(userAchievementService.getById(id));
    }

    @GetMapping("/get-all-user-achievement")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAll(){
        return ResponseEntity.ok(userAchievementService.getAllUserAchievement());
    }

    @GetMapping("/get-own-achievement")
    public ResponseEntity<Response> getOwnAchievement(){

        return ResponseEntity.ok(userAchievementService.getYourOwnAchievement());
    }
}
