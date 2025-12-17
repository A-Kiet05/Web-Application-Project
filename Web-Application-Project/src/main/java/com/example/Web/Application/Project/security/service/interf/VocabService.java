package com.example.Web.Application.Project.security.service.interf;

import com.example.Web.Application.Project.domain.dto.CategoryDTO;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.entities.Category;
import com.example.Web.Application.Project.domain.entities.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface VocabService {

            Response getAllCategories();
            Response createCategory(CategoryDTO categoryDTO);
            Response getWordsByCategory(Long categoryId, Pageable pageable);
}