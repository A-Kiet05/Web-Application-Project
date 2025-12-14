package com.example.Web.Application.Project.service.imple;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.domain.dto.CategoryDTO;
import com.example.Web.Application.Project.domain.entities.Category;
import com.example.Web.Application.Project.exception.NotFoundException;
import com.example.Web.Application.Project.mapper.Mapper;
import com.example.Web.Application.Project.repository.CategoryRepository;
import com.example.Web.Application.Project.service.interf.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImple implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper<Category,CategoryDTO> categoMapper;

    @Override
    public Response createCategory(CategoryDTO categoryDTO) {

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        categoryRepository.save(category);

        return Response.builder()
                .status(200)
                .message("Created Successfully!")
                .build();
    }

    @Override
    public Response getAllCategories() {

        List<CategoryDTO> list = categoryRepository.findAll(Sort.by(Sort.Direction.DESC , "id"))
                .stream()
                .map(categoMapper::mapTo)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .categoryDTOs(list)
                .build();
    }

    @Override
    public Response getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        CategoryDTO categoryDTO = categoMapper.mapTo(category);

        return Response.builder()
                .status(200)
                .category(categoryDTO)
                .build();
    }

    @Override
    public Response deleteCategory(Long id) {

        categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found!"));

        categoryRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Deleted Successfully!")
                .build();
    }
}
