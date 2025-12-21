package com.example.Web.Application.Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.service.interf.WrongWordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-words")
@RequiredArgsConstructor
public class UserWordController {

    private final WrongWordService wrongWordService;

    @PostMapping("/create-wrong-word")
    public ResponseEntity<Response> recordWrongWord( 
          @RequestParam (required = true) Long userId ,
          @RequestParam (required =  true)Long wordId
        ){
            return ResponseEntity.ok(wrongWordService.recordWrongWord(userId , wordId));
        }
    
    @GetMapping("/get-wrong-words-by-user/{id}")
    public ResponseEntity<Response> getByUserId(@PathVariable Long id){

        return ResponseEntity.ok(wrongWordService.getWrongWords(id));
    }

    @DeleteMapping("/delete-wrong-word/{id}")
   
    public ResponseEntity<Response> delete(@PathVariable Long id){

        return ResponseEntity.ok(wrongWordService.deleteWrongWord(id));
    }

    @DeleteMapping("/clear-all-wrong-word/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> clearAll(@PathVariable Long id){

        return ResponseEntity.ok(wrongWordService.clearUserWrongWords(id));
    }
}
