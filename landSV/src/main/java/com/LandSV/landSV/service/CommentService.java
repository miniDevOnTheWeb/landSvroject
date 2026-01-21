package com.LandSV.landSV.service;

import com.LandSV.landSV.dto.CommentDTO;
import com.LandSV.landSV.dto.CommentRequest;
import com.LandSV.landSV.entity.Comment;
import com.LandSV.landSV.entity.Post;
import com.LandSV.landSV.entity.User;
import com.LandSV.landSV.exceptions.ResourceNotFoundException;
import com.LandSV.landSV.mapper.CommentMapper;
import com.LandSV.landSV.repository.CommentRepository;
import com.LandSV.landSV.repository.PostRepository;
import com.LandSV.landSV.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentDTO createComment (CommentRequest request) {
        User user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(UUID.fromString(request.getPostId()))
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setCreatedAt(LocalDateTime.now());
        comment.setContent(request.getContent());
        comment.setPost(post);
        comment.setUser(user);

        Comment saved = commentRepository.save(comment);

        return CommentMapper.toDTO(saved);
    }

    public List<CommentDTO> getCommentsByPost(UUID postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        return comments.stream().map(CommentMapper::toDTO).toList();
    }
}
