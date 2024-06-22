package com.dev.backend.service;

import com.dev.backend.dto.PostDto;
import com.dev.backend.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> findById(Long id);

    List<UserDto> findAll();

    void deleteById(Long id);

    List<UserDto> followUser(Long followerId, Long followingId);

    List<UserDto> unFollowUser(Long followerId, Long followingId);

    List<PostDto> findPostById(Long id);
}
