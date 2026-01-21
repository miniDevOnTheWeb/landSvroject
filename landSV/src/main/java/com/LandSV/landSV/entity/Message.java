package com.LandSV.landSV.entity;
import com.LandSV.landSV.utils.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "chat_id", nullable = false, columnDefinition = "BINARY(16)")
    private Chat chat;

    @ManyToOne()
    @JoinColumn(name = "sender_id", nullable = false, columnDefinition = "BINARY(16)")
    private User sender;

    @ManyToOne()
    @JoinColumn(name = "receiver_id", nullable = false, columnDefinition = "BINARY(16)")
    private User receiver;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MessageType type;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();

    @PrePersist
    public void onCreate () {
        if(sentAt == null) {
            sentAt = LocalDateTime.now();
        }
    }

    public MessageType getType() {
        return type;
    }
    public User getReceiver() {
        return receiver;
    }
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Chat getChat() { return chat; }
    public void setChat(Chat chat) { this.chat = chat; }
    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}