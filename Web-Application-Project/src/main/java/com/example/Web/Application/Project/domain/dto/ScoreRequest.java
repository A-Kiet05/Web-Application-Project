package com.example.Web.Application.Project.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Builder
public class ScoreRequest {
    
    private String username;
    private Integer score;
}
