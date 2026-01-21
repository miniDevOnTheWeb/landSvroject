package com.LandSV.landSV.dto;

import com.LandSV.landSV.entity.Post;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    private String username;
    private UUID id;
    private String email;
    private String provider;
    private String profileImage;
    private List<PostDTO> posts;

    public List<PostDTO> getPosts() {
        return posts;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getProvider() {
        return provider;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public UUID getId() {
        return id;
    }
}
