package com.dev.backend.rest;

import com.dev.backend.dto.CategoryDto;
import com.dev.backend.entity.Category;
import com.dev.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    // POST request at /api/v1/categories
    @PostMapping("/categories")
    public CategoryDto addCategory(@Valid @RequestBody Category category) {
        return categoryService.save(category);
    }

    // GET request at /api/v1/categories/:id
    @GetMapping("/categories/{id}")
    public Optional<CategoryDto> getCategory(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    // GET request at /api/v1/categories/:id
    @GetMapping("/categories")
    public List<CategoryDto> getCategories() {
        return categoryService.findAll();
    }

    // DELETE request at /api/v1/categories/:id
    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
