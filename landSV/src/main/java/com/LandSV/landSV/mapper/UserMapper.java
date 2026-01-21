package com.LandSV.landSV.mapper;

import com.LandSV.landSV.dto.UserDTO;
import com.LandSV.landSV.entity.User;

public class UserMapper {
    public static UserDTO toUseDTO (User user) {
        UserDTO dto = new UserDTO();

        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setProvider(user.getProvider().name());
        dto.setProfileImage(user.getProfileImage());
        dto.setPosts(user.getPosts().stream().map(PostMapper::toPostDTO).toList());

        return dto;
    }
}
