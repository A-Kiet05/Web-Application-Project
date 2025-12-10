package com.example.Web.Application.Project.service.imple;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserStatsDTO;
import com.example.Web.Application.Project.domain.entities.GameSession;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.entities.UserStats;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.GameSessionRepository;
import com.example.Web.Application.Project.repository.UserRepository;
import com.example.Web.Application.Project.repository.UserStatsRepository;
import com.example.Web.Application.Project.service.interf.UserService;
import com.example.Web.Application.Project.service.interf.UserStatsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStatsServiceImpl implements UserStatsService {

    private final UserStatsRepository userStatsRepository;
    private final UserRepository userRepo;
    private final Mapper<UserStats,UserStatsDTO> userStatsMapper;
    private final GameSessionRepository gameSessionRepository;
    private final UserService userService;

    @Override
    public Response create(UserStats userStatsRequest) {
        User user = userRepo.findById(userStatsRequest.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Optional<UserStats> existing = userStatsRepository.findByUserId(user.getId());
        if(existing.isPresent()){
                throw new IllegalArgumentException("Stats already exist for this user");
        }

        UserStats userStats = calculateStats(user);
        userStatsRepository.save(userStats);

       return Response.builder()
                      .status(200)
                      .message("Created Successfully!")
                      .build();
    }

    @Override
    public Response update(Long id, UserStats userStatsRequest) {

        UserStats stats = userStatsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserStats not found"));

        User user = stats.getUser();
        UserStats updatedUserStats = calculateStats(user);


        userStatsRepository.save(updatedUserStats);

       return Response.builder()
                      .status(200)
                      .message("Updated Successfully!")
                      .build();
    }

    @Override
    public Response delete(Long id) {
        
        userStatsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserStats not found"));
        userStatsRepository.deleteById(id);

         return Response.builder()
                        .status(200)
                        .message("Deleted Successfully!")
                        .build();

    }

    @Override
    public Response getById(Long id) {
        
        UserStats userStats =  userStatsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserStats not found"));
        UserStatsDTO userStatsDTO = userStatsMapper.mapTo(userStats);

         return Response.builder()
                        .status(200)
                        .userStatsDTO(userStatsDTO)
                        .build();
        
    }

    @Override
    public Response getAll() {
        List<UserStatsDTO> userStatsDTOs = userStatsRepository.findAll(Sort.by(Sort.Direction.DESC,"id"))
                                                              .stream()
                                                              .map(userStatsMapper::mapTo)
                                                              .collect(Collectors.toList());

                 return Response.builder()
                                .status(200)
                                .userStatsDTOs(userStatsDTOs)
                                .build();     
    }
    
    //helper method
     private UserStats calculateStats(User user) {
         
        
        List<GameSession> sessions = gameSessionRepository.findByUserId(user.getId());

        if (sessions.isEmpty()) {
            throw new NotFoundException("User has no game sessions to calculate stats.");
        }

        int totalWordsTyped = sessions.stream()
                .mapToInt(GameSession::getWordsTyped)
                .sum();

        double bestWpm = sessions.stream()
                .mapToDouble(GameSession::getWpm)
                .max()
                .orElse(0);

        double avgAccuracy = sessions.stream()
                .mapToDouble(GameSession::getAccuracy)
                .average()
                .orElse(0);

        UserStats stats = new UserStats();
        stats.setUser(user);
        stats.setTotalWordsTyped(totalWordsTyped);
        stats.setBestWpm(bestWpm);
        stats.setAverageAccuracy(avgAccuracy);

        return stats;
    }
}