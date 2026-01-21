package com.LandSV.landSV.mapper;

import com.LandSV.landSV.dto.MessageDTO;
import com.LandSV.landSV.entity.Message;

public class MessageMapper {
    public static MessageDTO toMessageDTO (Message message) {
        MessageDTO dto = new MessageDTO();

        dto.setContent(message.getContent());
        dto.setCreatedAt(message.getSentAt());
        dto.setType(message.getType());
        dto.setReceiverId(message.getReceiver().getId());
        dto.setSenderId(message.getSender().getId());
        dto.setChatId(message.getChat().getId());
        dto.setId(message.getId());

        return dto;
    }
}
