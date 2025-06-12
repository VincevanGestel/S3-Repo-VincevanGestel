package com.example.spring_boot_rest_API.dto;

import com.example.spring_boot_rest_API.model.ChatMessage;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    public ChatMessageDTO(ChatMessage message) {
        this.sender = message.getSender();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();  // Make sure your ChatMessage has this field (optional)
    }
}
