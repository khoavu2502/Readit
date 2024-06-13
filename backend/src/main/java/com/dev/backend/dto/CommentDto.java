package com.dev.backend.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    private Long id;

    @NotBlank(message = "content cannot be empty")
    @Size(max = 65535, message = "content is too long")
    private String content;

    private Date createdAt;

    private UserDto user;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                      property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private PostDto post;
}
