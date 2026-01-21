package com.LandSV.landSV.controller;

import com.LandSV.landSV.dto.CommentDTO;
import com.LandSV.landSV.dto.CommentRequest;
import com.LandSV.landSV.dto.PostDTO;
import com.LandSV.landSV.dto.PostRequest;
import com.LandSV.landSV.service.CommentService;
import com.LandSV.landSV.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/getPostsByData")
    public ResponseEntity<?> getPostsByData (@RequestParam double maxPrice, @RequestParam String location) {
        List<PostDTO> posts = postService.getPostsByLocationAndMaxPrice(location, maxPrice);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "posts", posts
        ));
    }

    @GetMapping("/getComments/{postId}")
    public ResponseEntity<?> getCommentsByPost (@PathVariable UUID postId) {
        List<CommentDTO> comments = commentService.getCommentsByPost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "comments", comments
        ));
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost (@ModelAttribute PostRequest request) {
        PostDTO post = postService.createPost(request);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "post", post
        ));
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<?> commentPost (@RequestBody CommentRequest request) {
        CommentDTO comment = commentService.createComment(request);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "message", "Post commented",
                "comment", comment
        ));
    }

    @GetMapping("/getMyPosts/{userId}")
    public ResponseEntity<?> getMyPosts (@PathVariable UUID userId) {
        List<PostDTO> posts = postService.getPostByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "posts", posts
        ));
    }

    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<?> getPostById (@PathVariable UUID postId) {
        PostDTO post = postService.getPostById(postId);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "post", post
        ));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePostById (@PathVariable UUID postId) {
        postService.deletePostById(postId);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "message", "Publicacion eliminada"
        ));
    }
}
