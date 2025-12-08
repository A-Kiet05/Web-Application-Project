package com.example.Web.Application.Project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Web.Application.Project.domain.entities.Word;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

         @Query(value = "SELECT * FROM words ORDER BY RAND() LIMIT ?1", nativeQuery = true)
         List<Word> findRandomWords(int limit);
         List<Word> findByCategoryName(String categoryName );
}