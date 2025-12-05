package com.example.Web.Application.Project.mapper.imple;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.Web.Application.Project.domain.dto.UserDTO;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.mapper.Mapper;

@Component
public class UserMapperImple implements Mapper<User, UserDTO> {

    private ModelMapper modelMapper;

    public UserMapperImple(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public UserDTO mapTo(User user){
        return modelMapper.map(user , UserDTO.class);
    }

    @Override
    public User mapFrom(UserDTO userDTO){
        return modelMapper.map(userDTO , User.class);
    }
    
}
