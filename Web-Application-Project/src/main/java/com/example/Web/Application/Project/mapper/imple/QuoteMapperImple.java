package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.example.Web.Application.Project.domain.dto.QuoteDTO;
import com.example.Web.Application.Project.domain.entities.Quote;
import com.example.Web.Application.Project.mapper.Mapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuoteMapperImple implements Mapper<Quote, QuoteDTO> {

    private final ModelMapper modelMapper;

    @Override
    public QuoteDTO mapTo(Quote quote) {
        return modelMapper.map(quote, QuoteDTO.class);
    }

    @Override
    public Quote mapFrom(QuoteDTO quoteDTO) {
        return modelMapper.map(quoteDTO, Quote.class);
    }
}