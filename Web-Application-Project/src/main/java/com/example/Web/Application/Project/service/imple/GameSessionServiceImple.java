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
import com.example.Web.Application.Project.service.interf.AchievementEvaluatorService;
import com.example.Web.Application.Project.service.interf.GameSessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImple implements GameSessionService {

    private final GameSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final Mapper<GameSession, SessionResponse> sessionMapper;
    private final AchievementEvaluatorService achievementEvaluatorService;

    @Override
    public Response saveSession(SessionRequest sessionRequest) {

        User user = userRepository.findById(sessionRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        
                // ========== MONKEYTYPE AUTO CALCULATION ==========
        String typed = sessionRequest.getTypedText().trim();
        String original = sessionRequest.getOriginalText().trim();

        String[] typedArr = typed.split("\\s+");
        String[] originalArr = original.split("\\s+");

        int wordsTyped = typedArr.length;

        int correct = 0;
        int incorrect = 0;

        for (int i = 0; i < wordsTyped; i++) {
            if (i < originalArr.length && typedArr[i].equals(originalArr[i])) {
                correct++;
            } else {
                incorrect++;
            }
        }

        double accuracy = wordsTyped == 0 ? 0 : ((double) correct / wordsTyped) * 100.0;

        double durationSeconds = sessionRequest.getDuration();
        double durationMinutes = durationSeconds / 60.0;

        double rawWpm = wordsTyped / durationMinutes;
        double wpm = correct / durationMinutes;

        //=================================

        GameSession session = new GameSession();
        session.setUser(user);
        session.setCorrectWords(correct);
        session.setIncorrectWords(incorrect);
        session.setWordsTyped(wordsTyped);
        session.setDuration(sessionRequest.getDuration());
        session.setRawWpm(rawWpm);
        session.setWpm(wpm);
        session.setAccuracy(accuracy);
        session.setMode(sessionRequest.getMode());
        
       // save session
        sessionRepository.save(session);
        // trigger if that user unlock new achievement 
        achievementEvaluatorService.evaluateAchievementsForUser(user, accuracy, wpm);

        return Response.builder()
                .status(200)
                .message("Session Saved! And Achievement Evaluated !")
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
