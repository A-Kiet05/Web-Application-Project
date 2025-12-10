package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.example.Web.Application.Project.domain.dto.AchievementDTO;
import com.example.Web.Application.Project.domain.entities.Achievement;
import com.example.Web.Application.Project.mapper.Mapper;

@Component
public class AchievementMapperImple implements Mapper<Achievement, AchievementDTO> {

    private ModelMapper modelMapper;

    public AchievementMapperImple(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AchievementDTO mapTo(Achievement achievement) {
        return modelMapper.map(achievement, AchievementDTO.class);
    }

    @Override
    public Achievement mapFrom(AchievementDTO achievementDTO) {
        return modelMapper.map(achievementDTO, Achievement.class);
    }
}