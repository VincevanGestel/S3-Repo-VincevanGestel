package com.example.spring_boot_rest_API.service;

import com.example.spring_boot_rest_API.model.ChatMessage;
import com.example.spring_boot_rest_API.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return messageRepository.save(chatMessage);
    }

    public List<ChatMessage> getAllMessages() {
        return messageRepository.findAll();
    }
}
