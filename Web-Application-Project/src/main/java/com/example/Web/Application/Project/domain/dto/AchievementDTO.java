package com.example.Web.Application.Project.domain.dto;

import java.util.List;

import com.example.Web.Application.Project.domain.entities.UserAchievement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
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
public class AchievementDTO {
    

   
            private Long id;
            private String code;
            private String name;
            private String description;
            private String condition; // ex: "WPM_50", "ACCURACY_95"
            private List<UserAchievementResponse> userAchievements;
}
