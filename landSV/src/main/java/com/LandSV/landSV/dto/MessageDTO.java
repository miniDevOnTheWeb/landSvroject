package com.LandSV.landSV.dto;
import com.LandSV.landSV.utils.MessageType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MessageDTO {
    private String content;
    private UUID senderId;
    private UUID id;
    private UUID chatId;
    private UUID receiverId;
    private LocalDateTime createdAt;
    private MessageType type;

    // Getters Setters

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getChatId() {
        return chatId;
    }
    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }
    public UUID getReceiverId() {
        return receiverId;
    }
    public UUID getSenderId() {
        return senderId;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }
    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
