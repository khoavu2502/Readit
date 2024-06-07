package com.dev.backend.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostDto {

    private Long id;

    private String title;

    private String thumbnail;

    private String content;

    private boolean published;

    private Date createdAt;

    private Date updatedAt;

    private Date publishedAt;

    private UserDto user;

    private List<CommentDto> comments;

    private CategoryDto category;
}
