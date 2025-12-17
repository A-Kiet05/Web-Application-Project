package com.example.Web.Application.Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.WordDTO;
import com.example.Web.Application.Project.security.service.interf.WordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {
    
    private final WordService wordService;
   
    @PostMapping("/create-words")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createNewWord(@RequestBody WordDTO wordDTO){
        return ResponseEntity.ok(wordService.createNewWord(wordDTO));
    }


    @GetMapping("/all")
    public ResponseEntity<Response> getAllWords(){
        
        return ResponseEntity.ok(wordService.getAllWord());
    }

    @GetMapping("/get-word-by-id/{wordId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getWordById(@PathVariable Long wordId){
        return ResponseEntity.ok(wordService.getWordById(wordId));
    }

    @GetMapping("/get-random-word")
    public ResponseEntity<Response> getRandomWordInRange(@RequestParam (required = true , defaultValue = "10") int limit){
         return ResponseEntity.ok(wordService.getRandomWordInRange(limit));
    }

    @DeleteMapping("/delete-word/{wordId}")
     @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable Long wordId){
        return ResponseEntity.ok(wordService.deleteWord(wordId));
    }


}
