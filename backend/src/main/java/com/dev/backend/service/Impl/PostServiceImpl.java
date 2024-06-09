package com.dev.backend.service.Impl;

import com.dev.backend.dto.PostDto;
import com.dev.backend.entity.Post;
import com.dev.backend.exception.ResourceNotFoundException;
import com.dev.backend.repository.PostRepository;
import com.dev.backend.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @Override
    public PostDto save(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public Optional<PostDto> findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return Optional.ofNullable(modelMapper.map(optionalPost.get(), PostDto.class));
        } else {
            throw new ResourceNotFoundException("Cannot find post with id: " + id);
        }
    }

    @Override
    public List<PostDto> findAll() {

        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Cannot find post with id: " + id);
        }
    }
}
