package com.dev.backend.rest;

import com.dev.backend.dto.PostDto;
import com.dev.backend.entity.Post;
import com.dev.backend.service.PostService;
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
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    // POST request at /api/v1/posts
    @PostMapping("/posts")
    public PostDto addPost(@Valid @RequestBody Post post) {
        return postService.save(post);
    }

    // GET request at /api/v1/posts/:id
    @GetMapping("/posts/{id}")
    public Optional<PostDto> getPost(@PathVariable Long id) {
        return postService.findById(id);
    }

    // GET request at /api/v1/posts
    @GetMapping("/api/v1/posts")
    public List<PostDto> getPosts() {
        return postService.findAll();
    }

    // DELETE request at /api/v1/posts
    @DeleteMapping("/api/v1/posts")
    public void deletePost(@PathVariable Long id) {
        postService.deleteById(id);
    }
}
