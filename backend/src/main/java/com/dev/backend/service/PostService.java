package com.dev.backend.service;

import com.dev.backend.dto.PostDto;
import com.dev.backend.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PostDto save(PostDto postDto);

    Optional<PostDto> findById(Long id);

    List<PostDto> findAll();

    void deleteById(Long id);
}
