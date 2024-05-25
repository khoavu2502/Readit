package com.dev.backend.service;

import com.dev.backend.dto.CommentDto;
import com.dev.backend.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    CommentDto save(Comment comment);

    Optional<CommentDto> findById(Long id);

    List<CommentDto> findAll();

    void deleteById(Long id);
}
