package com.LandSV.landSV.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class CustomizedUserDetails implements UserDetails {
    private String username;
    private String email;
    private String id;
    private String password;
    private String profileImage;
    private List<SimpleGrantedAuthority> authorities;

    public CustomizedUserDetails (String password, String username, String email, String id, String profileImage, List<SimpleGrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.id = id;
        this.profileImage = profileImage;
        this.authorities = authorities;
    }

    public String getProfileImage() {
        return profileImage;
    }
    public String getUsername() {
        return username;
    }
    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
