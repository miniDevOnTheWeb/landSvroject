package com.LandSV.landSV.service;

import com.LandSV.landSV.dto.ChatDTO;
import com.LandSV.landSV.dto.ChatRequest;
import com.LandSV.landSV.entity.Chat;
import com.LandSV.landSV.entity.Message;
import com.LandSV.landSV.entity.User;
import com.LandSV.landSV.exceptions.DuplicatedException;
import com.LandSV.landSV.exceptions.ResourceNotFoundException;
import com.LandSV.landSV.mapper.ChatMapper;
import com.LandSV.landSV.repository.ChatRepository;
import com.LandSV.landSV.repository.MessageRepository;
import com.LandSV.landSV.repository.UserRepository;
import com.LandSV.landSV.utils.MessageType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public ChatDTO createChat (ChatRequest request) {
        if(chatRepository.existsByCreatorIdOrInvitedId(UUID.fromString(request.getUser1()), UUID.fromString(request.getUser2())))
            throw new DuplicatedException("The chat already exists");

        User firstUser = userRepository.findById(UUID.fromString(request.getUser1()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User secondUser = userRepository.findById(UUID.fromString(request.getUser2()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Chat chat = new Chat();
        chat.setCreator(firstUser);
        chat.setInvited(secondUser);
        chat.setHasUnseenMessages(true);
        chat.setLastSender(firstUser.getId().toString());

        Chat savedChat = chatRepository.save(chat);

        Message firstMessage = new Message();

        firstMessage.setChat(savedChat);
        firstMessage.setSender(firstUser);
        firstMessage.setContent(request.getFirstMessage());
        firstMessage.setReceiver(secondUser);
        firstMessage.setType(MessageType.PRIVATE);
        firstMessage.setSentAt(LocalDateTime.now());
        messageRepository.save(firstMessage);

        return ChatMapper.toChatDTO(savedChat);
    }

    public String deleteChat (String chatId) {
        try {
            Chat chat = chatRepository.findById(UUID.fromString(chatId))
                    .orElseThrow(() -> new ResourceNotFoundException("Chat doesn't exists"));

            chatRepository.delete(chat);

            return chat.getId().toString();
        } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
        }
    }

    public ChatDTO setUnseenMessages (String chatId) {
        System.out.println("entrando al service de chats");
        try {
            Chat chat = chatRepository.findById(UUID.fromString(chatId))
                    .orElseThrow(() -> new ResourceNotFoundException("Chat doesn't exists"));

            chat.setHasUnseenMessages(false);
            Chat saved = chatRepository.save(chat);
            return ChatMapper.toChatDTO(saved);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ChatDTO> getChatsByUser (String userId) {
        List<Chat> chats =
                chatRepository.findChatsByCreatorIdOrInvitedIdOrderByLastMessageDesc(
                        UUID.fromString(userId),
                        UUID.fromString(userId)
                );

        if(chats == null) chats = new ArrayList<>();

        return chats.stream().map(ChatMapper::toChatDTO).toList();
    }
}
