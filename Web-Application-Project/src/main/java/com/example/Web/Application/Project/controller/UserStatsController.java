package com.example.Web.Application.Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.entities.UserStats;
import com.example.Web.Application.Project.service.interf.UserStatsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-stats")
@RequiredArgsConstructor
public class UserStatsController {
    
    private final UserStatsService userStatsService;

    @PostMapping("/create-user-stats")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> create(@RequestBody UserStats userStats){

        return ResponseEntity.ok(userStatsService.create(userStats));
    }


    @PutMapping("/update-stats/${id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> update(@PathVariable Long id , @RequestBody UserStats userStats){

        return ResponseEntity.ok(userStatsService.update(id, userStats));
    }

    @DeleteMapping("/delete-stats/${id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable Long id ){
        return ResponseEntity.ok(userStatsService.delete(id));
    }

    @GetMapping("/get-by-id/${id}")
    @PreAuthorize("hasAuthority('ADMIN')")
     public ResponseEntity<Response> getById(@PathVariable Long id ){
        
         return ResponseEntity.ok(userStatsService.getById(id));
     }

     @GetMapping("/get-all-stats")
     @PreAuthorize("hasAuthority('ADMIN')")
     public ResponseEntity<Response> getAll(){

        return ResponseEntity.ok(userStatsService.getAll());
     }
}
