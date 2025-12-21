package com.example.Web.Application.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Web.Application.Project.domain.entities.UserWord;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserWordRepository extends JpaRepository<UserWord, Long> {
    
  Optional<UserWord> findByUserIdAndWordId(Long userId, Long wordId);

  List<UserWord> findByUserIdOrderByErrorCountDesc(Long userId);
  List<UserWord> findByUserId(Long userId);
  List<UserWord> findAllByUserId(Long userId);
}