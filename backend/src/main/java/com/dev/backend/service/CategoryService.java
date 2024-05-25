package com.dev.backend.service;

import com.dev.backend.dto.CategoryDto;
import com.dev.backend.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryDto save(Category category);

    Optional<CategoryDto> findById(Long id);

    List<CategoryDto> findAll();

    void deleteById(Long id);
}
