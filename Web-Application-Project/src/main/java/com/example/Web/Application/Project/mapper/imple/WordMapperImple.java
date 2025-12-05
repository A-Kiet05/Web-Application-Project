package com.example.Web.Application.Project.mapper.imple;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import com.example.Web.Application.Project.domain.dto.WordDTO;

import com.example.Web.Application.Project.domain.entities.Word;
import com.example.Web.Application.Project.mapper.Mapper;

@Component
public class WordMapperImple implements Mapper<Word , WordDTO>{
    

     private ModelMapper modelMapper;

    public WordMapperImple(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public WordDTO mapTo(Word word){
        return modelMapper.map(word , WordDTO.class);
    }

    @Override
    public Word mapFrom(WordDTO wordDTO){
        return modelMapper.map(wordDTO , Word.class);
    }
}
