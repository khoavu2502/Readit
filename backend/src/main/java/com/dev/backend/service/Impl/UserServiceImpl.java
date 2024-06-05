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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

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

    @Override
    public List<UserDto> followUser(Long followerId, Long followingId) {
        List<UserDto> response = new ArrayList<>();
        Optional<User> optionalFollower = userRepository.findById(followerId);
        Optional<User> optionalFollowing = userRepository.findById(followingId);

        if (optionalFollower.isPresent() && optionalFollowing.isPresent()) {
            User follower = optionalFollower.get();
            User following = optionalFollowing.get();

            following.getFollowers().add(follower);
            follower.getFollowing().add(following);

            response.add(modelMapper.map(userRepository.save(follower), UserDto.class));
            response.add(modelMapper.map(userRepository.save(following), UserDto.class));
        }
        return response;
    }

    @Override
    public List<UserDto> unFollowUser(Long followerId, Long followingId) {
        List<UserDto> response = new ArrayList<>();
        Optional<User> optionalFollower = userRepository.findById(followerId);
        Optional<User> optionalFollowing = userRepository.findById(followingId);

        if (optionalFollower.isPresent() && optionalFollowing.isPresent()) {
            User follower = optionalFollower.get();
            User following = optionalFollowing.get();

            follower.getFollowing().remove(following);
            following.getFollowers().remove(follower);

            response.add(modelMapper.map(userRepository.save(follower), UserDto.class));
            response.add(modelMapper.map(userRepository.save(following), UserDto.class));
        }
        return response;
    }
}
