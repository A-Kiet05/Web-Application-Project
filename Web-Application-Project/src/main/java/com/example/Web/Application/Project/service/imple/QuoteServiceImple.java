package com.example.Web.Application.Project.service.imple;

import org.springframework.stereotype.Service;
import com.example.Web.Application.Project.domain.dto.QuoteDTO;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.entities.Quote;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.QuoteRepository;
import com.example.Web.Application.Project.service.interf.QuoteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuoteServiceImple implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final Mapper<Quote, QuoteDTO> quoteMapper;

    @Override
    public Response getRandomQuote() {
        Quote quote = quoteRepository.findRandomQuote();

        if (quote == null) {
            throw new NotFoundException("No quotes found in database.");
        }

        QuoteDTO quoteDTO = quoteMapper.mapTo(quote);

        return Response.builder()
                .status(200)
                .message("Random quote fetched successfully")
                .quoteDTO(quoteDTO)
                .build();
    }
}