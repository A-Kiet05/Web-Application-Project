package com.example.Web.Application.Project.service.imple;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.CategoryDTO;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.WordDTO;
import com.example.Web.Application.Project.domain.entities.Category;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.entities.Word;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.CategoryRepository;
import com.example.Web.Application.Project.repository.WordRepository;
import com.example.Web.Application.Project.service.interf.CategoryService;
import com.example.Web.Application.Project.service.interf.UserService;
import com.example.Web.Application.Project.service.interf.WordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WordServiceImple implements WordService{
    
    private final WordRepository wordRepository;
    private final UserService userService;
    private final Mapper<Word, WordDTO> wordMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Response createNewWord(WordDTO wordDTO ){
         
        User user = userService.getLogin();
        if(user != null && user.getRole().toString().equals("USER")){
             throw new IllegalArgumentException("Only Admin can create new word !");
        }

        Word word = new Word();
        word.setWord(wordDTO.getWord());

        wordRepository.save(word);

        return Response.builder()
                       .status(200)
                       .message("Created Successfully!")
                       .build();

        

    }

    @Override
    public Response getAllWord(){
        
        List<WordDTO> wordDTOs = wordRepository.findAll().stream().map(wordMapper::mapTo).collect(Collectors.toList());

          return Response.builder()
                       .status(200)
                       .wordDTOs(wordDTOs)
                       .build();
    }

    @Override
    public Response getWordById(Long wordId){
        
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new NotFoundException("Word desired not found!"));
        WordDTO wordDTO = wordMapper.mapTo(word);

          return Response.builder()
                       .status(200)
                       .wordDTO(wordDTO)
                       .build();
    }

    @Override
    public Response getRandomWordInRange(int limit){
       
        if( limit <=0 ){
            limit = 1;
        }
        List<WordDTO> wordDTOs = wordRepository.findRandomWords(limit).stream().map(wordMapper::mapTo).collect(Collectors.toList());

          return Response.builder()
                       .status(200)
                       .wordDTOs(wordDTOs)
                       .build();
    }

    @Override
    public Response deleteWord(Long wordId){
            
        wordRepository.findById(wordId).orElseThrow(() -> new NotFoundException("id not found!"));
        wordRepository.deleteById(wordId);

          return Response.builder()
                       .status(200)
                       .message("Deleted Successfully!")
                       .build();
    }
    
    @Override
    public Response getAllWordByCategories(String categoryName){
        
        
        List<Word> word = wordRepository.findByCategoryName(categoryName);
        
        if(word.isEmpty()){
            throw new NotFoundException("word by category not found!");
        }

        List<WordDTO> wordDTOs = word.stream().map(wordMapper::mapTo).collect(Collectors.toList());

         return Response.builder()
                       .status(200)
                       .wordDTOs(wordDTOs)
                       .build();


    }

   

}
