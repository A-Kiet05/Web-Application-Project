package com.example.Web.Application.Project.domain.dto;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.example.Web.Application.Project.domain.entities.User;
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

    private ScoreDTO scoreDTO;
    private List<ScoreDTO> scoreDTOs;

    private WordDTO wordDTO;
    private List<WordDTO> wordDTOs;

    
}
