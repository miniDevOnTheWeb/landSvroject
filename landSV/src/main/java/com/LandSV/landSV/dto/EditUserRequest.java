package com.LandSV.landSV.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class EditUserRequest {
    private MultipartFile image;
    private String username;

    @NotBlank(message = "La informacion del usuario esta ausente")
    private String userId;

    @NotBlank(message = "La contrasena es obligatoria")
    private String password;

    public String getUserId() {
        return userId;
    }
    public MultipartFile getImage() {
        return image;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setImage(MultipartFile image) {
        this.image = image;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
