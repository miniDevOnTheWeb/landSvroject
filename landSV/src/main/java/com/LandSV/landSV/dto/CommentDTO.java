package com.LandSV.landSV.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private String id;
    private LocalDateTime createdAt;
    private String content;
    private String postId;
    private String creator;
    private String creatorId;

    public String getCreatorId() {
        return creatorId;
    }
    public String getCreator() {
        return creator;
    }
    public String getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getPostId() {
        return postId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
}
