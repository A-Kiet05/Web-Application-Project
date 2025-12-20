package com.example.Web.Application.Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.service.interf.QuoteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping("/random")
    public ResponseEntity<Response> getRandomQuote() {
        return ResponseEntity.ok(quoteService.getRandomQuote());
    }
}