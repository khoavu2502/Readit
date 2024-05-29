package com.dev.backend.service.Impl;

import com.dev.backend.dto.CategoryDto;
import com.dev.backend.entity.Category;
import com.dev.backend.exception.ResourceNotFoundException;
import com.dev.backend.repository.CategoryRepository;
import com.dev.backend.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto save(Category category) {
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return Optional.ofNullable(modelMapper.map(optionalCategory, CategoryDto.class));
        } else {
            throw new ResourceNotFoundException("Cannot find category with id: " + id);
        }
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Cannot find category with id: " + id);
        }
    }
}
