package com.LandSV.landSV.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ChatDTO {
    private String id;
    private String creator;
    private String creatorId;
    private String invited;
    private boolean hasUnseenMessages;
    private String invitedId;
    private String lastSenderId;
    private LocalDate createdAt;
    private LocalDateTime lastMessage;
    private List<MessageDTO> messages;
    private String invitedProfileImage;
    private String creatorProfileImage;

    public String getCreatorProfileImage() {
        return creatorProfileImage;
    }
    public String getInvitedProfileImage() {
        return invitedProfileImage;
    }
    public void setCreatorProfileImage(String creatorProfileImage) {
        this.creatorProfileImage = creatorProfileImage;
    }
    public void setInvitedProfileImage(String invitedProfileImage) {
        this.invitedProfileImage = invitedProfileImage;
    }
    public boolean isHasUnseenMessages() {
        return hasUnseenMessages;
    }
    public String getLastSenderId() {
        return lastSenderId;
    }
    public void setLastSenderId(String lastSenderId) {
        this.lastSenderId = lastSenderId;
    }
    public LocalDateTime getLastMessage() {
        return lastMessage;
    }
    public void setHasUnseenMessages(boolean hasUnseenMessages) {
        this.hasUnseenMessages = hasUnseenMessages;
    }
    public LocalDateTime getLastMessages() {
        return lastMessage;
    }
    public void setLastMessage(LocalDateTime lastMessage) {
        this.lastMessage = lastMessage;
    }
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    public void setInvitedId(String invitedId) {
        this.invitedId = invitedId;
    }
    public String getCreatorId() {
        return creatorId;
    }
    public String getInvitedId() {
        return invitedId;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public void setInvited(String invited) {
        this.invited = invited;
    }
    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public List<MessageDTO> getMessages() {
        return messages;
    }
    public String getCreator() {
        return creator;
    }
    public String getId() {
        return id;
    }
    public String getInvited() {
        return invited;
    }
}
