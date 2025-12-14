package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.Web.Application.Project.domain.dto.UserStatsDTO;
import com.example.Web.Application.Project.domain.entities.UserStats;
import com.example.Web.Application.Project.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserStatsMapperImple implements Mapper<UserStats, UserStatsDTO>{
    
    private final ModelMapper modelMapper;

    @Override
    public UserStatsDTO mapTo(UserStats userStats){
        return modelMapper.map(userStats, UserStatsDTO.class);
    }

    @Override
    public UserStats mapFrom(UserStatsDTO userStatsDTO){
        return modelMapper.map(userStatsDTO, UserStats.class);
    }
}
