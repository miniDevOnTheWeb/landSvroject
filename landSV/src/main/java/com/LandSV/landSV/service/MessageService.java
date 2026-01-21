package com.LandSV.landSV.service;

import com.LandSV.landSV.dto.MessageDTO;
import com.LandSV.landSV.entity.Chat;
import com.LandSV.landSV.entity.Message;
import com.LandSV.landSV.entity.User;
import com.LandSV.landSV.exceptions.ResourceNotFoundException;
import com.LandSV.landSV.mapper.MessageMapper;
import com.LandSV.landSV.repository.ChatRepository;
import com.LandSV.landSV.repository.MessageRepository;
import com.LandSV.landSV.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public MessageDTO saveMessage (MessageDTO dto) {
        if (dto.getContent() == null || dto.getContent().isBlank()) {
            throw new IllegalArgumentException("The message text can't be empty");
        }
        if (dto.getSenderId() == null || dto.getReceiverId() == null) {
            throw new IllegalArgumentException("Users data can't be null");
        }

        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        Chat chat = chatRepository.findByCreatorIdOrInvitedId(dto.getSenderId(), dto.getReceiverId())
                .orElseGet(() -> {
                    Chat newChat = new Chat();

                    newChat.setCreator(sender);
                    newChat.setInvited(receiver);

                    return chatRepository.save(newChat);
                });

        Message message = new Message();

        message.setContent(dto.getContent());
        message.setType(dto.getType());
        message.setChat(chat);
        message.setSender(sender);
        message.setReceiver(receiver);

        chat.setLastMessage(LocalDateTime.now());
        chat.setHasUnseenMessages(true);
        chat.setLastSender(sender.getId().toString());

        chatRepository.save(chat);

        Message savedMessage = messageRepository.save(message);

        return MessageMapper.toMessageDTO(savedMessage);
    }
}
