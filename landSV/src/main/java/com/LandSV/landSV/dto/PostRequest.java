package com.LandSV.landSV.dto;

import com.LandSV.landSV.entity.Message;
import com.LandSV.landSV.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostRequest {
    @NotBlank(message = "El usuario creador esta ausente")
    private String description;

    @NotBlank(message = "El usuario creador esta ausente")
    private List<MultipartFile> images;

    @NotBlank(message = "La ubicacion es obligatoria")
    private String location;

    @NotBlank(message = "El precio de la propiedad es obligatorio")
    private double price;

    @NotBlank(message = "La informacion del creador esta ausente")
    private String userId;

    @NotBlank(message = "El telefono de contacto es obligatorio")
    private String phoneNumber;

    public String getLocation() {
        return location;
    }
    public double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public List<MultipartFile> getImages() {
        return images;
    }
    public String getUserId() {
        return userId;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setImages(List<MultipartFile> images) {
        this.images = images != null ? images : new ArrayList<>();
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
