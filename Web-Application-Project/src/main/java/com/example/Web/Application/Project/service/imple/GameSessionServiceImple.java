package com.example.Web.Application.Project.service.imple;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.SessionRequest;
import com.example.Web.Application.Project.domain.dto.SessionResponse;
import com.example.Web.Application.Project.domain.entities.GameSession;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.GameSessionRepository;
import com.example.Web.Application.Project.repository.UserRepository;
import com.example.Web.Application.Project.service.interf.GameSessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImple implements GameSessionService {

    private final GameSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final Mapper<GameSession, SessionResponse> sessionMapper;

    @Override
    public Response saveSession(SessionRequest sessionRequest) {

        User user = userRepository.findById(sessionRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        

        GameSession session = new GameSession();
        session.setUser(user);
        session.setDuration(sessionRequest.getDuration());
        session.setWordsTyped(sessionRequest.getWordsTyped());
        session.setCorrectWords(sessionRequest.getCorrectWords());
        session.setIncorrectWords(sessionRequest.getIncorrectWords());
        session.setRawWpm(sessionRequest.getRawWpm());
        session.setWpm(sessionRequest.getWpm());
        session.setAccuracy(sessionRequest.getAccuracy());
        session.setMode(sessionRequest.getMode());
        

        sessionRepository.save(session);

        return Response.builder()
                .status(200)
                .message("Session Saved!")
                .build();
    }

    @Override
    public Response getSessionsByUser(Long userId) {

        List<SessionResponse> sessionsResponses = sessionRepository.findByUserId(userId)
                .stream()
                .map(sessionMapper::mapTo)
                .collect(Collectors.toList());

        if(sessionsResponses == null){
                throw new NotFoundException("userId not found !");
        }

         return Response.builder()
                        .status(200)
                        .sessionResponses(sessionsResponses)
                        .build();
    }
}
