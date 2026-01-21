package com.LandSV.landSV.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {
    @NotBlank(message = "El comentario no debe de estar vacio")
    private String content;

    @NotBlank(message = "La informacion del post esta vacia")
    private String postId;

    @NotBlank(message = "La informacion del usuario esta vacia")
    private String userId;

    public String getContent() {
        return content;
    }
    public String getUserId() {
        return userId;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
