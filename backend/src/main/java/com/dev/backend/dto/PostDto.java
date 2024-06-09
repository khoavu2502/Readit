package com.dev.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostDto {

    private Long id;

    @NotBlank(message = "title cannot be empty")
    @Size(max = 75, message = "{validation.name.size.too_long}")
    private String title;

    @NotBlank(message = "thumbnail cannot be empty")
    private String thumbnail;

    @NotBlank(message = "content cannot be empty")
    @Size(max = 65535, message = "content is too long")
    private String content;

    private boolean published;

    private Date createdAt;

    private Date updatedAt;

    private Date publishedAt;

    private UserDto user;

    private List<CommentDto> comments;

    @NotNull(message = "category is required")
    private CategoryDto category;
}
