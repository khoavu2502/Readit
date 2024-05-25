package com.dev.backend.service.Impl;

import com.dev.backend.dto.CommentDto;
import com.dev.backend.entity.Comment;
import com.dev.backend.exception.ResourceNotFoundException;
import com.dev.backend.repository.CommentRepository;
import com.dev.backend.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentDto save(Comment comment) {
        return modelMapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public Optional<CommentDto> findById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            return Optional.ofNullable(modelMapper.map(optionalComment.get(), CommentDto.class));
        } else {
            throw new ResourceNotFoundException("Cannot find comment with id: " + id);
        }
    }

    @Override
    public List<CommentDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Cannot find user with id: " + id);
        }
    }
}
