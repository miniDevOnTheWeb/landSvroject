package com.LandSV.landSV.service;

import com.LandSV.landSV.entity.CustomizedUserDetails;
import com.LandSV.landSV.entity.User;
import com.LandSV.landSV.exceptions.ResourceNotFoundException;
import com.LandSV.landSV.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomizedUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomizedUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new CustomizedUserDetails(
                user.getPassword(),
                user.getUsername(),
                user.getEmail(),
                user.getId().toString(),
                user.getProfileImage(),
                authorities
        );
    }
}
