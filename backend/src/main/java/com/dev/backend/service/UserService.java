package com.dev.backend.service;

import com.dev.backend.dto.UserDto;
import com.dev.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> findById(Long id);

    List<UserDto> findAll();

    void deleteById(Long id);
}
