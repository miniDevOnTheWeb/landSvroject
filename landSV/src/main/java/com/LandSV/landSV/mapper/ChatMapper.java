package com.LandSV.landSV.mapper;

import com.LandSV.landSV.dto.ChatDTO;
import com.LandSV.landSV.entity.Chat;

public class ChatMapper {
    public static ChatDTO toChatDTO (Chat chat) {
        ChatDTO dto = new ChatDTO();

        dto.setCreator(chat.getCreator().getUsername());
        dto.setLastSenderId(chat.getLastSender());
        dto.setInvited(chat.getInvited().getUsername());
        dto.setId(chat.getId().toString());
        dto.setCreatedAt(chat.getCreatedAt());
        dto.setCreatorId(chat.getCreator().getId().toString());
        dto.setCreatorProfileImage(chat.getCreator().getProfileImage());
        dto.setInvitedProfileImage(chat.getInvited().getProfileImage());
        dto.setInvitedId(chat.getInvited().getId().toString());
        dto.setMessages(chat.getMessages().stream()
                .map(MessageMapper::toMessageDTO)
                .toList());
        dto.setLastMessage(chat.getLastMessage());
        dto.setHasUnseenMessages(chat.isHasUnseenMessages());

        return dto;
    }
}
