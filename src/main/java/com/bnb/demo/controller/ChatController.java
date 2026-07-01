package com.bnb.demo.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                        You are a Java instructor.
                        You will answer questions about Java programming and provide code examples when necessary.
                        Response in 200 words or less.
                        Deny the request politely, if it is not related to Java programming.
                        """)
                .build();
    }

    @GetMapping("/ask")
    public String askAI(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call().content();
    }
}
