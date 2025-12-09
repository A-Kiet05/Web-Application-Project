package com.example.Web.Application.Project.service.imple;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserWordDTO;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.entities.UserWord;
import com.example.Web.Application.Project.domain.entities.Word;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.UserRepository;
import com.example.Web.Application.Project.repository.UserWordRepository;
import com.example.Web.Application.Project.repository.WordRepository;
import com.example.Web.Application.Project.service.interf.WrongWordService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WrongWordServiceImpl implements WrongWordService {

    private final UserWordRepository wrongWordRepository;
    private final Mapper<UserWord , UserWordDTO> wrongMapper;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;

    @Override
    public Response recordWrongWord(Long userId, Long wordId) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new NotFoundException("Word not found"));

        UserWord wrong = wrongWordRepository.findByUserIdAndWordId(userId, wordId)
        .orElse(
                UserWord.builder()
                        .user(user)
                        .word(word)
                        .errorCount(0)
                        .build()
        );
        
       
        wrong.setErrorCount(wrong.getErrorCount() + 1);
        wrongWordRepository.save(wrong);

        return Response.builder()
                       .status(200)
                       .message("record wrong word successfully!")
                       .build();
        
    }

    @Override
    public Response getWrongWords(Long userId) {
        
        List<UserWord> userWords = wrongWordRepository.findByUserId(userId);
        List<UserWordDTO> userWordDTOs= userWords.stream().map(wrongMapper::mapTo).collect(Collectors.toList());
      

         return Response.builder()
                       .status(200)
                       .userWordDTOs(userWordDTOs)
                       .build();

    }

    @Override
    public Response deleteWrongWord(Long id) {
        
        wrongWordRepository.findById(id).orElseThrow(() -> new NotFoundException("id not found!"));
        wrongWordRepository.deleteById(id);

        return Response.builder()
                       .status(200)
                       .message("deleted Successfully !")
                       .build();
    }

    @Override
    public Response clearUserWrongWords(Long userId) {

        List<UserWord> wrongWords = wrongWordRepository.findAllByUserId(userId);
                wrongWords.forEach(word -> wrongWordRepository.deleteById(word.getId()));


                return Response.builder()
                       .status(200)
                       .message("Clear all successfully!")
                       .build();
    }
}
