package com.example.Web.Application.Project.service.interf;

import com.example.Web.Application.Project.domain.dto.Response;

public interface WrongWordService {
    
    Response recordWrongWord(Long userId, Long wordId);

    Response getWrongWords(Long userId);

    Response deleteWrongWord(Long id);

    Response clearUserWrongWords(Long userId);
}
