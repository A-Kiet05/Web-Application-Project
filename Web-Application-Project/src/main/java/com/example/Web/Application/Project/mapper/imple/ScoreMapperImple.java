package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.Web.Application.Project.domain.dto.ScoreDTO;

import com.example.Web.Application.Project.domain.entities.Score;

import com.example.Web.Application.Project.mapper.Mapper;

public class ScoreMapperImple implements Mapper<Score, ScoreDTO>{
    

    private ModelMapper modelMapper;

    public ScoreMapperImple(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public ScoreDTO mapTo(Score score){
        return modelMapper.map(score , ScoreDTO.class);
    }

    @Override
    public Score mapFrom(ScoreDTO scoreDTO){
        return modelMapper.map(scoreDTO , Score.class);
    }
}
