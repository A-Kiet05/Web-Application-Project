package com.example.Web.Application.Project.service.imple;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.ScoreRequest;
import com.example.Web.Application.Project.domain.dto.ScoreResponse;
import com.example.Web.Application.Project.domain.entities.Score;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.ScoreRepository;
import com.example.Web.Application.Project.repository.UserRepository;
import com.example.Web.Application.Project.service.interf.ScoreService;
import com.example.Web.Application.Project.service.interf.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreServiceImple implements ScoreService{
    
    private final ScoreRepository scoreRepository;
    private final UserService userService;
    private final Mapper<Score , ScoreResponse> scoreMapper;
    private final UserRepository userRepository;

    @Override
    public Response postScore(ScoreRequest scoreRequest){
         
        User user = userRepository.findByEmail(scoreRequest.getEmail()).orElseThrow(() -> new NotFoundException("Email not found!"));

       //===== Calculate score============
       String typed = scoreRequest.getTypedText()
        .toLowerCase()
        .trim()
        .replaceAll("[^a-z0-9\\s]", "");

        String original = scoreRequest.getOriginalText()
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s]", "");


        String[] typedArr = typed.split("\\s+");
        String[] originalArr = original.split("\\s+");

        int totalWords = originalArr.length;
        int correct = 0;

        for (int i = 0; i < typedArr.length; i++) {
            if (i < totalWords && typedArr[i].equals(originalArr[i])) {
                correct++;
            }
        }

        BigDecimal finalScore =
        BigDecimal.valueOf(correct)
            .divide(BigDecimal.valueOf(totalWords), 2, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(100));

        //=========================
        Score score = new Score();
        score.setScore(finalScore);
        score.setUser(user);

        scoreRepository.save(score);
       
        return Response.builder()
                       .status(200)
                       .message(" Successfully!")
                       .build();
        
        
    }

     @Override
    public Response getAllScore(){
          
        List<ScoreResponse> scoreDTOs = scoreRepository.findAll(Sort.by(Sort.Direction.DESC , "id"))
                                                  .stream()
                                                  .map(scoreMapper::mapTo)
                                                  .collect(Collectors.toList());

                    return Response.builder()
                       .status(200)
                       .scoreDTOs(scoreDTOs)
                       .build();
    }

     @Override
    public Response leaderBoard(int top ,Pageable pageable ){
            
        List<Score> scores = scoreRepository.findTopScores(PageRequest.of(0, top));
        List<ScoreResponse> scoreDTOs = scores.stream().map(scoreMapper::mapTo).collect(Collectors.toList());

        return Response.builder()
                       .status(200)
                       .scoreDTOs(scoreDTOs)
                       .build();
    }

    
}
