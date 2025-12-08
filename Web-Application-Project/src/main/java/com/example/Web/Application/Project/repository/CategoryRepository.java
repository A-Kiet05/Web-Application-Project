package com.example.Web.Application.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Web.Application.Project.domain.entities.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
    

