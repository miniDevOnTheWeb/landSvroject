package com.LandSV.landSV.mapper;

import com.LandSV.landSV.dto.CommentDTO;
import com.LandSV.landSV.entity.Comment;

public class CommentMapper {
    public static CommentDTO toDTO (Comment comment) {
        CommentDTO dto = new CommentDTO();

        dto.setCreator(comment.getUser().getUsername());
        dto.setId(comment.getId().toString());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setPostId(comment.getPost().getId().toString());
        dto.setCreatorId(comment.getUser().getId().toString());

        return dto;
    }
}
