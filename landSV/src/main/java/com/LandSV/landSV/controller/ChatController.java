package com.LandSV.landSV.controller;

import com.LandSV.landSV.dto.ChatDTO;
import com.LandSV.landSV.dto.ChatRequest;
import com.LandSV.landSV.entity.Chat;
import com.LandSV.landSV.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/getUserChats/{userId}")
    public ResponseEntity<?> getUserChats (@PathVariable String userId) {
        List<ChatDTO> chats = chatService.getChatsByUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "chats", chats
        ));
    }

    @PostMapping("/setSeenMessages/{id}")
    public ResponseEntity<?> setSeenMessages (@PathVariable String id) {
        ChatDTO saved = chatService.setUnseenMessages(id);

        System.out.println("chat");
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "message", "Messages marked as seen",
                "chat", saved
        ));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChat (@RequestBody ChatRequest request) {
        System.out.println("chat processing");
        ChatDTO saved = chatService.createChat(request);

        System.out.println("chat");
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "message", "Chat created successfully",
                "chat", saved
        ));
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<?> deleteChat (@PathVariable String chatId) {
        chatService.deleteChat(chatId);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "message", "Chat removed successfully"
        ));
    }
}
