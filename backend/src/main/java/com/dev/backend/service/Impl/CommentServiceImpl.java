package com.dev.backend.service.Impl;

import com.dev.backend.dto.CommentDto;
import com.dev.backend.dto.UserDto;
import com.dev.backend.entity.Comment;
import com.dev.backend.exception.ResourceNotFoundException;
import com.dev.backend.repository.CommentRepository;
import com.dev.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final AuthenticationService authenticationService;

    @Override
    public CommentDto save(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
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
    public void deleteById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Long currentUserId = authenticationService.getCurrentUser().getId();
            Long ownerId = optionalComment.get().getUser().getId();

            if (!currentUserId.equals(ownerId)) {
                throw new AccessDeniedException("Access Denied");
            }

            commentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Cannot find comment with id: " + id);
        }
    }
}
