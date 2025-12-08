package com.example.Web.Application.Project.service.imple;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserWordDTO;
import com.example.Web.Application.Project.domain.entities.UserWord;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.UserWordRepository;
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

    @Override
    public Response recordWrongWord(Long userId, Long wordId) {

        UserWord wrong = wrongWordRepository.findByUserIdAndWordId(userId, wordId).orElseThrow(() -> new NotFoundException("user id or word id not found!"));
        
       
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

        wrongWordRepository.findAllByUserId(userId)
                .forEach(item -> wrongWordRepository.deleteById(item.getId()));


                return Response.builder()
                       .status(200)
                       .message("Clear all successfully!")
                       .build();
    }
}
