package com.example.Web.Application.Project.service.interf;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.WordDTO;
import com.example.Web.Application.Project.domain.entities.Quote;

public interface QuoteService {
    Response getRandomQuote();
}
