package com.example.spring_boot_rest_API.controller;

import com.example.spring_boot_rest_API.model.ChatMessage;
import com.example.spring_boot_rest_API.model.User;
import com.example.spring_boot_rest_API.service.MessageService;
import com.example.spring_boot_rest_API.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final MessageService messageService;
    /*
    @MessageMapping("/chat")
    public void handleChat(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Retrieve username from WebSocket session attributes
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username == null) {
            throw new RuntimeException("User not authenticated");
        }

        User sender = userService.findEntityByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save chat message, associate sender username
        chatMessage.setSender(sender.getUsername());
        ChatMessage savedMessage = messageService.saveMessage(chatMessage);

        // Broadcast saved message to subscribers
        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
    }
    */
    @MessageMapping("/chat")
    public void handleChat(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // TEMP: skip checking session auth
        chatMessage.setSender(chatMessage.getSender());
        messagingTemplate.convertAndSend("/topic/messages", chatMessage);
    }

    @MessageMapping("/join")
    public void handleJoin(@Payload ChatMessage joinMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = joinMessage.getSender();
        if (username == null || username.isBlank()) {
            throw new RuntimeException("Invalid username");
        }

        // Find existing user or handle user creation logic
        User user = userService.findEntityByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Store username in WebSocket session
        headerAccessor.getSessionAttributes().put("username", user.getUsername());

        // Optionally, broadcast user online status
        messagingTemplate.convertAndSend("/topic/users", user.getUsername() + " joined the chat");
    }
}
