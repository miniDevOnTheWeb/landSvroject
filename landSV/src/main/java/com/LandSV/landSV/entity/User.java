package com.LandSV.landSV.entity;

import com.LandSV.landSV.utils.UserProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "profile_image", nullable = true)
    private String profileImage;

    @Column(name = "provider", nullable = true)
    private UserProvider provider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "creator", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Chat> createdChats = new ArrayList<>();

    @OneToMany(mappedBy = "invited", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Chat> invitedChats = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public List<Post> getPosts() { return posts; }
    public void setPosts(List<Post> posts) {this.posts = posts;}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public UserProvider getProvider() { return provider; }
    public void setProvider(UserProvider provider) { this.provider = provider; }
}
