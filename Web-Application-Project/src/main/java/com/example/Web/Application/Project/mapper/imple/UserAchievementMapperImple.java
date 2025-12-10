package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.example.Web.Application.Project.domain.dto.UserAchievementResponse;
import com.example.Web.Application.Project.domain.entities.UserAchievement;
import com.example.Web.Application.Project.mapper.Mapper;

@Component
public class UserAchievementMapperImple implements Mapper<UserAchievement, UserAchievementResponse> {

    private ModelMapper modelMapper;

    public UserAchievementMapperImple(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserAchievementResponse mapTo(UserAchievement userAchievement) {
        return modelMapper.map(userAchievement, UserAchievementResponse.class);
    }

    @Override
    public UserAchievement mapFrom(UserAchievementResponse response) {
        return modelMapper.map(response, UserAchievement.class);
    }
}