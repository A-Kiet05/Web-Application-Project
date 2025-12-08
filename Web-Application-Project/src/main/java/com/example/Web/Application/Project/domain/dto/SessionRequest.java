package com.example.Web.Application.Project.domain.dto;

import com.example.Web.Application.Project.domain.enums.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionRequest {

        private Long userId;
        private int duration;
         private UserDTO user; 
        private int wordsTyped;
        private int correctWords;
        private int incorrectWords;
        private double rawWpm;
        private double wpm;
        private double accuracy;
        private Mode mode;
}
