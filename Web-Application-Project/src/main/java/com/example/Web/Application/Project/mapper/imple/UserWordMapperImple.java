package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.Web.Application.Project.domain.dto.UserDTO;
import com.example.Web.Application.Project.domain.dto.UserWordDTO;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.entities.UserWord;
import com.example.Web.Application.Project.mapper.Mapper;

@Component
public class UserWordMapperImple implements Mapper<UserWord , UserWordDTO> {
    
    private ModelMapper modelMapper;
    public UserWordMapperImple(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public UserWordDTO mapTo(UserWord userwWord){
        return modelMapper.map(userwWord , UserWordDTO.class);
    }

    @Override
    public UserWord mapFrom(UserWordDTO userWordDTO){
        return modelMapper.map(userWordDTO , UserWord.class);
    }
}
