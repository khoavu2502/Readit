package com.dev.backend.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                  property = "id")
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

    private List<CategoryDto> categories;
}
