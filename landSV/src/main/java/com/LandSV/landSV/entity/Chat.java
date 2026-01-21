package com.LandSV.landSV.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "creator_id", columnDefinition = "BINARY(16)", nullable = false)
    private User creator;

    @ManyToOne()
    @JoinColumn(name = "invited_id", columnDefinition = "BINARY(16)", nullable = false)
    private User invited;

    @JoinColumn(name = "last_sender_id", columnDefinition = "BINARY(16)", nullable = true)
    private String lastSender = "";

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sentAt ASC")
    private List<Message> messages = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "last_message", nullable = true)
    private LocalDateTime lastMessage;

    @Column(name = "has_unseen_messages", nullable = false)
    private boolean hasUnseenMessages = false;

    public String getLastSender() {
        return lastSender;
    }
    public void setLastSender(String lastSender) {
        this.lastSender = lastSender;
    }
    public LocalDateTime getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(LocalDateTime lastMessage) {
        this.lastMessage = lastMessage;
    }
    public void setHasUnseenMessages(boolean hasUnseenMessages) {
        this.hasUnseenMessages = hasUnseenMessages;
    }
    public boolean isHasUnseenMessages() {
        return hasUnseenMessages;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }
    public User getInvited() { return invited; }
    public void setInvited(User invited) { this.invited = invited; }
    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
}
