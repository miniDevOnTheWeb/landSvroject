package com.LandSV.landSV.socket;

import com.LandSV.landSV.dto.ChatDTO;
import com.LandSV.landSV.dto.MessageDTO;
import com.LandSV.landSV.repository.UserRepository;
import com.LandSV.landSV.service.ChatService;
import com.LandSV.landSV.service.MessageService;
import com.LandSV.landSV.utils.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HandleMessagesSocket extends TextWebSocketHandler {
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final ChatService chatService;
    Map<String, WebSocketSession> onlineUsers = new ConcurrentHashMap<>();
    private final ObjectMapper mapper;

    public HandleMessagesSocket(MessageService messageService, UserRepository userRepository, ChatService chatService, ObjectMapper mapper) {
        this.messageService = messageService;
        this.userRepository = userRepository;
        this.chatService = chatService;
        this.mapper = mapper;
    }

    @Override
    public void afterConnectionEstablished (WebSocketSession session) throws IOException {
        String identifier = extractIdentifier(session);
        if(identifier == null) {
            MessageDTO message = new MessageDTO();
            message.setType(MessageType.SYSTEM);
            message.setContent("The data for the register is invalid");
            message.setCreatedAt(LocalDateTime.now());

            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
            return;
        }

        onlineUsers.put(identifier, session);
        System.out.println("User connected");
    }

    @Override
    public void handleTextMessage (WebSocketSession session, TextMessage message) throws IOException {
        MessageDTO msg = mapper.readValue(message.getPayload(), MessageDTO.class);

        try{
            WebSocketSession receiver = onlineUsers.get(msg.getReceiverId().toString());

            switch (msg.getType()) {
                case PRIVATE:
                    MessageDTO saved = messageService.saveMessage(msg);

                    if(receiver != null && receiver.isOpen()) {
                        receiver.sendMessage(new TextMessage(mapper.writeValueAsString(saved)));
                        sendNotification(receiver, "You have new messages");
                    }
                    if(session.isOpen()) {
                        session.sendMessage(new TextMessage(mapper.writeValueAsString(saved)));
                    }
                    break;
                case SEEN_CHAT:
                    ChatDTO setAsSeed = chatService.setUnseenMessages(msg.getContent());

                    MessageDTO signal = new MessageDTO();
                    signal.setChatId(UUID.fromString(setAsSeed.getId()));
                    signal.setType(MessageType.SEEN_CHAT);
                    signal.setCreatedAt(LocalDateTime.now());

                    if(receiver != null && receiver.isOpen()) {
                        receiver.sendMessage(new TextMessage(mapper.writeValueAsString(signal)));
                    }

                    if(session.isOpen()) {
                        session.sendMessage(new TextMessage(mapper.writeValueAsString(signal)));
                    }
                    break;
                case DELETE_CHAT:
                    String deleted = chatService.deleteChat(msg.getContent());
                    System.out.println("deleted");
                    System.out.println(deleted);

                    MessageDTO deleteMessage = new MessageDTO();
                    deleteMessage.setChatId(UUID.fromString(deleted));
                    deleteMessage.setType(MessageType.DELETE_CHAT);
                    deleteMessage.setCreatedAt(LocalDateTime.now());

                    System.out.println(deleteMessage.getType());

                    if(receiver != null && receiver.isOpen()) {
                        receiver.sendMessage(new TextMessage(mapper.writeValueAsString(deleteMessage)));
                    }

                    if(session.isOpen()) {
                        session.sendMessage(new TextMessage(mapper.writeValueAsString(deleteMessage)));
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            MessageDTO error = new MessageDTO();
            error.setType(MessageType.SYSTEM);
            error.setContent(ex.getMessage());
            error.setCreatedAt(LocalDateTime.now());

            if(session.isOpen()) {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(error)));
            }
        }
    }

    public void sendNotification(WebSocketSession receiver, String content) throws IOException {
        if (receiver != null && receiver.isOpen()) {
            MessageDTO notification = new MessageDTO();
            notification.setType(MessageType.NOTIFICATION);
            notification.setContent(content);
            notification.setCreatedAt(LocalDateTime.now());
            receiver.sendMessage(new TextMessage(mapper.writeValueAsString(notification)));
        }
    }

    @Override
    public void afterConnectionClosed (WebSocketSession session, CloseStatus status) throws IOException {
        onlineUsers.entrySet().removeIf(entry -> entry.getValue().equals(session));
        System.out.println("disconnected");
    }

    private String extractIdentifier (WebSocketSession session) {
        String query = session.getUri().getQuery();
        if(!query.startsWith("userId")) return null;
        String identifier = query.split("=")[1];

        if(!userRepository.findById(UUID.fromString(identifier)).isPresent()) return null;

        return identifier;
    }
}















