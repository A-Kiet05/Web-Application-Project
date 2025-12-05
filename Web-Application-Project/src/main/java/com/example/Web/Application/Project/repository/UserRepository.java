package com.example.Web.Application.Project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Web.Application.Project.domain.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


       Optional<User> findByUsername(String username);
       boolean existsByUsername(String username);
       Optional<User> findByEmail(String email);
}
    

