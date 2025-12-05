package com.example.Web.Application.Project.domain.dto;

import java.util.List;

import com.example.Web.Application.Project.domain.entities.Score;
import com.example.Web.Application.Project.domain.enums.UserRole;
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
public class UserDTO {
    

   
  private Long id;
  private String username;
  private String password; // hashed
  private String email;
  private String fullName;
  private UserRole role;
  private List<Score> scores;
}
