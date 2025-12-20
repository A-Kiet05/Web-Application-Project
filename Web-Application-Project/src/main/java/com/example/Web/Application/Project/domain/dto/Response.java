package com.example.Web.Application.Project.domain.dto;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.example.Web.Application.Project.domain.entities.Achievement;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.entities.UserStats;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Response {
    
    private int status;
    private String message;
    private final LocalDateTime timestamp= LocalDateTime.now();


    private String token;
    private String role;
    private String expirationTime;

    private int totalPages;
    private long totalElement;

    private UserDTO user;
    private List<UserDTO> userDTOs;

    private ScoreResponse scoreDTO;
    private List<ScoreResponse> scoreDTOs;

    private WordDTO wordDTO;
    private List<WordDTO> wordDTOs;

    private CategoryDTO category;
    private List<CategoryDTO> categoryDTOs;
   
    private SessionResponse sessionResponse;
    private List<SessionResponse> sessionResponses;

   private UserWordDTO userWordDTO;
   private List<UserWordDTO> userWordDTOs;

   private AchievementDTO achievement;
   private List<AchievementDTO> achievementDTOs;

   private UserAchievementResponse userAchievementResponse;
   private List<UserAchievementResponse> userAchievementResponses;
   
   private UserStatsDTO userStatsDTO;
   private List<UserStatsDTO> userStatsDTOs;

   private QuoteDTO quoteDTO;
   private List<QuoteDTO> quoteDTOs;

}
