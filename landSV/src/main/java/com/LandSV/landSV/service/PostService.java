package com.LandSV.landSV.service;

import com.LandSV.landSV.dto.PostDTO;
import com.LandSV.landSV.dto.PostRequest;
import com.LandSV.landSV.entity.Post;
import com.LandSV.landSV.entity.PostImage;
import com.LandSV.landSV.entity.User;
import com.LandSV.landSV.exceptions.ResourceNotFoundException;
import com.LandSV.landSV.mapper.PostMapper;
import com.LandSV.landSV.repository.PostImageRepository;
import com.LandSV.landSV.repository.PostRepository;
import com.LandSV.landSV.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final PostImageRepository postImageRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CloudinaryService cloudinaryService, PostImageRepository postImageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.postImageRepository = postImageRepository;
    }

    public List<PostDTO> getPostsByLocationAndMaxPrice(String location, double minPrice) {
        List<Post> posts = postRepository.findByLocationAndMaxValue(location, minPrice);
        return posts.stream().map(PostMapper::toPostDTO).toList();
    }

    public PostDTO createPost (PostRequest request) {
        User user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = new Post();

        if (request.getImages() == null || request.getImages().isEmpty()) throw new IllegalArgumentException("Images are required");

        post.setCreatedAt(LocalDateTime.now());
        post.setLocation(request.getLocation());
        post.setDescription(request.getDescription());
        post.setPrice(request.getPrice());
        post.setPhoneNumber(request.getPhoneNumber());
        post.setUser(user);

        Post saved = postRepository.save(post);

        List<String> images = new ArrayList<>();
        try {
            request.getImages().forEach(imageFile -> {
                String url = null;
                try {
                    url = cloudinaryService.uploadImage(imageFile);
                } catch (IOException e) {
                    postRepository.delete(post);
                    throw new RuntimeException("Intentelo mas tarde");
                }
                images.add(url);
            });

            List<PostImage> imagesToSave = new ArrayList<>();

            images.forEach(image -> {
                PostImage postImage = new PostImage();
                postImage.setUrl(image);
                postImage.setPost(post);
                PostImage savedImage = postImageRepository.save(postImage);
                imagesToSave.add(savedImage);
            });

            post.setImages(imagesToSave);
            Post savedPost = postRepository.save(post);

            return PostMapper.toPostDTO(savedPost);
        } catch (Exception e) {
            postRepository.delete(post);
            throw new RuntimeException("Intentelo mas tarde");
        }
    }

    public PostDTO getPostById(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return PostMapper.toPostDTO(post);
    }

    public void deletePostById(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        postRepository.delete(post);
    }

    public List<PostDTO> getPostByUserId (UUID id) {
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(id);

        return posts.stream().map(PostMapper::toPostDTO).toList();
    }
}
