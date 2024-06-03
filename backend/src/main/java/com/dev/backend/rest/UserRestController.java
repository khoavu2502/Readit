package com.dev.backend.rest;

import com.dev.backend.dto.UserDto;
import com.dev.backend.entity.User;
import com.dev.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    // GET request at /api/v1/users/:id
    @GetMapping("/users/{id}")
    public Optional<UserDto> getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    // GET request at /api/v1/users
    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.findAll();
    }

    // DELETE request at /api/v1/users/:id
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
