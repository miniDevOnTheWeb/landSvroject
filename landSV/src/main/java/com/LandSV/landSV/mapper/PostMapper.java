package com.LandSV.landSV.mapper;

import com.LandSV.landSV.dto.PostDTO;
import com.LandSV.landSV.entity.Post;

public class PostMapper {
    public static PostDTO toPostDTO (Post post) {
        PostDTO dto = new PostDTO();

        dto.setCreator(post.getUser().getUsername());
        dto.setCreatorId(post.getUser().getId().toString());
        dto.setId(post.getId().toString());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setDescription(post.getDescription());
        dto.setLocation(post.getLocation());
        dto.setPrice(post.getPrice());
        dto.setImages(post.getImages().stream().map(image -> image.getUrl()).toList());
        dto.setPhoneNumber(post.getPhoneNumber());

        return dto;
    }
}
