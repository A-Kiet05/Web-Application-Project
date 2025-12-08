package com.example.Web.Application.Project.service.imple;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserAchievementRequest;
import com.example.Web.Application.Project.domain.dto.UserAchievementResponse;
import com.example.Web.Application.Project.domain.entities.Achievement;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.entities.UserAchievement;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.AchievementRepository;
import com.example.Web.Application.Project.repository.UserAchievementRepository;
import com.example.Web.Application.Project.repository.UserRepository;
import com.example.Web.Application.Project.service.interf.UserAchievementService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAchievementServiceImpl implements UserAchievementService{

    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final Mapper<UserAchievement,UserAchievementResponse> userAchievementMapper;

    @Override
    public Response create(UserAchievementRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Achievement achievement = achievementRepository.findById(request.getAchievementId())
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setUser(user);
        userAchievement.setAchievement(achievement);
       

       userAchievementRepository.save(userAchievement);

        return Response.builder()
                       .status(200)
                       .message("Created Successfully!")
                       .build();

    }

    @Override
    public Response update(Long id, UserAchievementRequest request) {

        UserAchievement userAchievement = userAchievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserAchievement not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Achievement achievement = achievementRepository.findById(request.getAchievementId())
                .orElseThrow(() -> new NotFoundException("Achievement not found"));

        if(user != null) userAchievement.setUser(user);
        if(achievement != null) userAchievement.setAchievement(achievement);

       UserAchievement savedUserAchievement = userAchievementRepository.save(userAchievement);
       UserAchievementResponse userAchievementResponse = userAchievementMapper.mapTo(savedUserAchievement);

        return Response.builder()
                       .status(200)
                       .message("Updated Successfully!")
                       .userAchievementResponse(userAchievementResponse)
                       .build();
    }

    @Override
    public Response delete(Long id) {
       
         userAchievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserAchievement not found"));
        userAchievementRepository.deleteById(id);

        return Response.builder()
                       .status(200)
                       .message("Deleted Successfully!")
                       .build();
    }

    @Override
    public Response getById(Long id) {
         
            UserAchievement userAchievement = userAchievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserAchievement not found"));

          UserAchievement savedUserAchievement = userAchievementRepository.save(userAchievement);
          UserAchievementResponse userAchievementResponse = userAchievementMapper.mapTo(savedUserAchievement);


          return Response.builder()
                       .status(200)
                       .userAchievementResponse(userAchievementResponse)
                       .build();
    }

    @Override
    public Response getAllUserAchievement() {
        
        List<UserAchievementResponse> responseList = userAchievementRepository.findAll(Sort.by(Sort.Direction.DESC,"id"))
                                        .stream()
                                        .map(userAchievementMapper::mapTo)
                                        .collect(Collectors.toList());


                 return Response.builder()
                                .status(200)
                                .userAchievementResponses(responseList)
                                .build();
    }
}
