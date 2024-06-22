package com.dev.backend.rest;

import com.dev.backend.dto.PostDto;
import com.dev.backend.dto.UserDto;
import com.dev.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserController {

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

    // POST request at /api/v1/users/:followerId/follow/:followingId
    @PostMapping("/users/{followerId}/follow/{followingId}")
    public ResponseEntity<?> follow(@PathVariable Long followerId,
                                         @PathVariable Long followingId) {
        List<UserDto> response = userService.followUser(followerId, followingId);
        return ResponseEntity.ok(response);
    }

    // POST request at /api/v1/users/:followerId/follow/:followingId
    @PostMapping("/users/{followerId}/unfollow/{followingId}")
    public ResponseEntity<?> unFollow(@PathVariable Long followerId,
                                           @PathVariable Long followingId) {
        List<UserDto> response =  userService.unFollowUser(followerId, followingId);
        return ResponseEntity.ok(response);
    }

    // GET request at /api/v1/users/:id/posts
    @GetMapping("/users/{id}/posts")
    public List<PostDto> getPosts(@PathVariable Long id) {
        return userService.findPostById(id);
    }

    // DELETE request at /api/v1/users/:id
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
