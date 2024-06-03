package com.dev.backend.service.Impl;

import com.dev.backend.dto.LoginDto;
import com.dev.backend.dto.UserDto;
import com.dev.backend.entity.User;
import com.dev.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public UserDto register(UserDto userData) {
        userData.setPassword(bCryptPasswordEncoder.encode(userData.getPassword()));
        User user = modelMapper.map(userData, User.class);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public UserDto getCurrentUser() {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authUser.getName();
        return modelMapper.map(userRepository.findByUsername(currentUsername), UserDto.class);
    }

    public User authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        return userRepository.findByUsername(loginDto.getUsername());
    }
}
