package com.example.Web.Application.Project.security.service.imple;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.AchievementDTO;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.entities.Achievement;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.AchievementRepository;
import com.example.Web.Application.Project.security.service.interf.AchievementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementServiceImple implements AchievementService{

    private final AchievementRepository achievementRepository;
    private final Mapper<Achievement, AchievementDTO> achievementMapper;

    @Override
    public Response createAchievement(AchievementDTO achievementDTO) {

        Achievement achievement = new Achievement();
        achievement.setCode(achievementDTO.getCode());
        achievement.setDescription(achievementDTO.getDescription());
        achievement.setConditionType(achievementDTO.getConditionType());
        achievement.setName(achievementDTO.getName());

        achievementRepository.save(achievement);

        return Response.builder()
                .status(200)
                .message("Achievement Created Successfully!")
                .build();
    }

    @Override
    public Response getAllAchievements() {

        List<AchievementDTO> dtos = achievementRepository.findAll()
                .stream()
                .map(achievementMapper::mapTo)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .achievementDTOs(dtos)
                .build();
    }

    @Override
    public Response getAchievementById(Long id) {

        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Achievement not found!"));

        AchievementDTO achievementDTO = achievementMapper.mapTo(achievement);

        return Response.builder()
                .status(200)
                .achievement(achievementDTO)
                .build();
    }

    @Override
    public Response deleteAchievement(Long id) {

        achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Achievement not found!"));

        achievementRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Achievement deleted successfully!")
                .build();
    }
}

