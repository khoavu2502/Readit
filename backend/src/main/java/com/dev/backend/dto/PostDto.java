package com.dev.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private boolean published;

    private Date createdAt;

    private Date updatedAt;

    private Date publishedAt;
}
