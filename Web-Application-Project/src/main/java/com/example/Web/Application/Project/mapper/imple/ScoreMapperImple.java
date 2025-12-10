package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.Web.Application.Project.domain.dto.ScoreResponse;

import com.example.Web.Application.Project.domain.entities.Score;

import com.example.Web.Application.Project.mapper.Mapper;

@Component
public class ScoreMapperImple implements Mapper<Score, ScoreResponse>{
    

    private ModelMapper modelMapper;

    public ScoreMapperImple(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public ScoreResponse mapTo(Score score){
        return modelMapper.map(score , ScoreResponse.class);
    }

    @Override
    public Score mapFrom(ScoreResponse scoreDTO){
        return modelMapper.map(scoreDTO , Score.class);
    }
}
