package com.example.Web.Application.Project.mapper.imple;



import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.Web.Application.Project.domain.dto.CategoryDTO;
import com.example.Web.Application.Project.domain.entities.Category;
import com.example.Web.Application.Project.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryMapperImple implements Mapper<Category , CategoryDTO>{

    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO mapTo(Category category){
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public Category mapFrom(CategoryDTO categoryDTO){
        return modelMapper.map(categoryDTO, Category.class);
    }
    
}
