package com.example.Web.Application.Project.service.interf;

import com.example.Web.Application.Project.domain.dto.CategoryDTO;
import com.example.Web.Application.Project.domain.dto.Response;

public interface CategoryService {
    
    Response createCategory(CategoryDTO categoryDTO);
    Response getAllCategories();
    Response getCategoryById(Long id);
    Response deleteCategory(Long id);
}
