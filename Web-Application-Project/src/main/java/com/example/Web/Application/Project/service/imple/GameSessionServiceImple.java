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
import com.example.Web.Application.Project.service.interf.ScoreService;
import com.example.Web.Application.Project.service.interf.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameSessionServiceImple implements GameSessionService {

    private final GameSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final Mapper<GameSession, SessionResponse> sessionMapper;
    private final AchievementEvaluatorService achievementEvaluatorService;
    private final UserService userService;
    private final ScoreService scoreService;

    @Override
    public Response saveSession(SessionRequest sessionRequest) {

        User user = userRepository.findById(sessionRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found!"));

        // Handle Null Strings (Prevent NullPointerException)
        String typed = sessionRequest.getTypedText() != null ? sessionRequest.getTypedText().trim() : "";
        String original = sessionRequest.getOriginalText() != null ? sessionRequest.getOriginalText().trim() : "";

        //===== Auto save score ==========
        scoreService.postScore(user.getEmail(), typed, original);


        // ========== MONKEYTYPE AUTO CALCULATION ==========
        String[] typedArr = typed.isEmpty() ? new String[0] : typed.split("\\s+");
        String[] originalArr = original.isEmpty() ? new String[0] : original.split("\\s+");

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

        // Calculate Accuracy (handle 0 words typed)
        double accuracy = wordsTyped == 0 ? 0 : ((double) correct / wordsTyped) * 100.0;

        // fix divide by zero bug
        double durationSeconds = sessionRequest.getDuration();
        if (durationSeconds <= 1) {
            durationSeconds = 1; // default to 1 second if 0 to prevent Infinity
        }

        double durationMinutes = durationSeconds / 60.0;

        double rawWpm = wordsTyped / durationMinutes;
        double wpm = correct / durationMinutes;


        GameSession session = new GameSession();
        session.setUser(user);
        session.setCorrectWords(correct);
        session.setIncorrectWords(incorrect);
        session.setWordsTyped(wordsTyped);
        session.setDuration((int) Math.ceil(durationSeconds)); // Save the safe duration
        session.setRawWpm(rawWpm);
        session.setWpm(wpm);
        session.setAccuracy(accuracy);

        // Ensure Mode is not null (defaults to TIME if missing, or handle in entity)
        session.setMode(sessionRequest.getMode());

        // save session
        sessionRepository.save(session);

        // trigger achievement check
        achievementEvaluatorService.evaluateAchievementsForUser(user, accuracy, wpm);

        return Response.builder()
                .status(200)
                .message("Session Saved Successfully!")
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

    @Override
    public  Response getYourOwnSession(){
        
        User user = userService.getLogin();

          List<SessionResponse> sessionResponses = sessionRepository.findByUserId(user.getId())
                                                                    .stream()
                                                                    .map(sessionMapper::mapTo)
                                                                    .collect(Collectors.toList());
         return Response.builder()
                        .status(200)
                        .sessionResponses(sessionResponses)
                        .build();
    }
}
