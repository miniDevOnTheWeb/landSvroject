package com.LandSV.landSV.config;

import com.LandSV.landSV.service.ChatService;
import com.LandSV.landSV.service.MessageService;
import com.LandSV.landSV.repository.UserRepository;
import com.LandSV.landSV.socket.HandleMessagesSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final com.LandSV.landSV.service.ChatService chatService;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public WebSocketConfig(MessageService messageService, UserRepository userRepository, com.LandSV.landSV.service.ChatService chatService, com.fasterxml.jackson.databind.ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.userRepository = userRepository;
        this.chatService = chatService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public HandleMessagesSocket HandleMessagesSocket () { return new HandleMessagesSocket(messageService, userRepository, chatService, objectMapper); }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new HandleMessagesSocket(messageService, userRepository, chatService, objectMapper), "/ws/chats")
                .setAllowedOrigins("*");
    }
}
