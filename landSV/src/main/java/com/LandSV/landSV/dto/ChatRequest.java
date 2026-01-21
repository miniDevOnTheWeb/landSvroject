package com.LandSV.landSV.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {
    @NotBlank(message = "El usuario creador esta ausente")
    private String user1;

    @NotBlank(message = "El usuario invitado esta ausente")
    private String user2;

    @NotBlank(message = "El primer mensaje no puede estar vacio")
    private String firstMessage;

    public void setFirstMessage(String firstMessage) {
        this.firstMessage = firstMessage;
    }
    public void setUser1(String user1) {
        this.user1 = user1;
    }
    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getFirstMessage() {
        return firstMessage;
    }
    public String getUser1() {
        return user1;
    }
    public String getUser2() {
        return user2;
    }
}

