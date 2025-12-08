package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.Web.Application.Project.domain.dto.SessionResponse;
import com.example.Web.Application.Project.domain.entities.GameSession;
import com.example.Web.Application.Project.mapper.Mapper;

@Component
public class GameSessionMapperImple implements Mapper<GameSession, SessionResponse>{
    

    private ModelMapper modelMapper;

    public GameSessionMapperImple(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public SessionResponse mapTo(GameSession gameSession){
        return modelMapper.map(gameSession , SessionResponse.class);
    }

    @Override
    public GameSession mapFrom(SessionResponse sessionRequest){
        return modelMapper.map(sessionRequest , GameSession.class);
    }
}
