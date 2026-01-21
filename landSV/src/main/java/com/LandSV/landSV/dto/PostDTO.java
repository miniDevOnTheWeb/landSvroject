package com.LandSV.landSV.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {
    private String id;
    private String description;
    private List<String> images;
    private LocalDateTime createdAt;
    private String creator;
    private String creatorId;
    private String location;
    private String phoneNumber;
    private double price;
    private List<CommentDTO> comments;


    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    public String getCreatorId() {
        return creatorId;
    }
    public String getLocation() {
        return location;
    }
    public double getPrice() {
        return price;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getCreator() {
        return creator;
    }
    public List<CommentDTO> getComments() {
        return comments;
    }
    public List<String> getImages() {
        return images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }
}
