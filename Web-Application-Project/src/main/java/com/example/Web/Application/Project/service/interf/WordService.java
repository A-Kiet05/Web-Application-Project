package com.example.Web.Application.Project.service.interf;

import org.springframework.http.ResponseEntity;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.WordDTO;
import com.example.Web.Application.Project.domain.entities.Word;

public interface WordService {
    
    Response createNewWord(WordDTO wordDTO );
    Response getAllWord();
    Response getWordById(Long wordId);
    Response getRandomWordInRange(int limit);
    Response deleteWord(Long wordId);

}
