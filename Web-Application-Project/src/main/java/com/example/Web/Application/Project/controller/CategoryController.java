package com.example.Web.Application.Project.controller;

import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Web.Application.Project.domain.dto.CategoryDTO;
import com.example.Web.Application.Project.domain.dto.Response;
import com.example.Web.Application.Project.service.interf.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;

    @PostMapping("/create-category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> create(@RequestBody CategoryDTO categoryDTO){

        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/get-category-by-id/${id}")
    public ResponseEntity<Response> getById(@PathVariable Long id){

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/delete-category/${id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
