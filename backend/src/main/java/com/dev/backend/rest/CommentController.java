package com.dev.backend.rest;

import com.dev.backend.dto.CommentDto;
import com.dev.backend.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    // POST request at /api/v1/comments
    @PostMapping("/comments")
    public CommentDto addComment(@Valid @RequestBody CommentDto commentDto) {
        return commentService.save(commentDto);
    }

    // GET request at /api/v1/comments/:id
    @GetMapping("/comments/{id}")
    public Optional<CommentDto> getComment(@PathVariable Long id) {
        return commentService.findById(id);
    }

    // GET request at /api/v1/comments
    @GetMapping("/comments")
    public List<CommentDto> getComments() {
        return commentService.findAll();
    }

    // DELETE request at /api/v1/comments/{id}
    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}
