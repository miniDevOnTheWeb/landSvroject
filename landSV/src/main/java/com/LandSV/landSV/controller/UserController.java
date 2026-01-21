package com.LandSV.landSV.controller;

import com.LandSV.landSV.dto.ChatDTO;
import com.LandSV.landSV.dto.EditUserRequest;
import com.LandSV.landSV.dto.UserDTO;
import com.LandSV.landSV.dto.UserRegisterRequest;
import com.LandSV.landSV.entity.Chat;
import com.LandSV.landSV.service.ChatService;
import com.LandSV.landSV.service.MailService;
import com.LandSV.landSV.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ChatService chatService;

    public UserController(UserService userService, ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser (@ModelAttribute UserRegisterRequest request) throws IOException {
        UserDTO user = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", HttpStatus.CREATED,
                "message", "User registered successfully",
                "user", user
        ));
    }

    @PostMapping(value = "/editUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editUser (@ModelAttribute EditUserRequest request) throws Exception {
        UserDTO user = userService.editUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", HttpStatus.CREATED,
                "message", "Usuario editad exitosamente",
                "user", user
        ));
    }
}
