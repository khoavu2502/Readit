package com.dev.backend.service.Impl;

import com.dev.backend.dto.UserDto;
import com.dev.backend.entity.User;
import com.dev.backend.exception.ResourceNotFoundException;
import com.dev.backend.repository.UserRepository;
import com.dev.backend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto save(User user) {
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return Optional.ofNullable(modelMapper.map(optionalUser.get(), UserDto.class));
        } else {
            throw new ResourceNotFoundException("Cannot find user with id: " + id);
        }
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Cannot find user with id: " + id);
        }
    }
}
