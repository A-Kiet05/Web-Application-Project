package com.example.Web.Application.Project.service.imple;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.LoginRequest;
import com.example.Web.Application.Project.domain.dto.RegisterRequest;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.UserDTO;
import com.example.Web.Application.Project.domain.entities.User;
import com.example.Web.Application.Project.domain.enums.UserRole;
import com.example.Web.Application.Project.exception.InvalidCredentialsException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.UserRepository;
import com.example.Web.Application.Project.security.JwtUtils;
import com.example.Web.Application.Project.service.interf.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImple implements UserService{
    
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final Mapper<User,UserDTO> userMapper;
    private final PasswordEncoder passwordEncoder;

    
    @Override
    public Response registryUser(RegisterRequest registrationRequest){
        
        UserRole role = UserRole.USER;
        
        if(registrationRequest.getRole()!= null && registrationRequest.getRole().equals(UserRole.ADMIN)){
               role = UserRole.ADMIN;
        }

        User user = User.builder()
        .username(registrationRequest.getUsername())
        .email(registrationRequest.getEmail())
        .password(passwordEncoder.encode(registrationRequest.getPassword()))
        .fullName(registrationRequest.getFullName())
        .role(role)
        .build();

        User savedUser = userRepository.save(user);

        UserDTO userDTO = userMapper.mapTo(savedUser);

        return Response.builder()
                       .status(200)
                       .message("Registry successfully!!")
                       .user(userDTO)
                       .build();
                        
                        
                     
    }
    
    @Override
    public Response loginUser(LoginRequest loginRequest){
       
        User user = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Email not found!"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
              throw new InvalidCredentialsException("Password does not match ");
        }

        String token = jwtUtils.generateToken(user);

        return Response.builder()
                       .status(200)
                       .message("User successfully login!")
                       .token(token)
                       .expirationTime("6 months")
                       .role(user.getRole().name())
                       .build();
    }

    @Override
    public Response getAllUser(){
        
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = userList.stream().map(userMapper::mapTo).collect(Collectors.toList());

        return Response.builder()
                       .status(200)
                       .userDTOs(userDTOList)
                       .build();
    }

    @Override
    public User getLogin(){
         
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("User email is " + email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email Not Found!!"));
    }


   
   

}
