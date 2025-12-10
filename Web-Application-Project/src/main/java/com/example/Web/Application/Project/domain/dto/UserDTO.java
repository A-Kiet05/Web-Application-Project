package com.example.Web.Application.Project.domain.dto;

import java.util.List;


import com.example.Web.Application.Project.domain.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    

   
      private Long id;
      private String username;
      private String email;
      private String fullName;
      private UserRole role;
      private List<ScoreResponse> scores;
      private List<SessionResponse> gameSessions;
      private List<UserAchievementResponse> userAchievements;
      private List<UserWordDTO> userWords; 
      private UserStatsDTO userStats;

}
