package com.bnb.demo.controller;


import com.bnb.demo.advisors.TokenUsageCustomAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

//    public ChatController(ChatClient.Builder builder, ChatMemory memory) {
//        this.chatClient = builder
//                .defaultAdvisors(List.of(MessageChatMemoryAdvisor.builder(memory).build(),
//                        new SimpleLoggerAdvisor()))
//                .defaultSystem("""
//                        You are a Java instructor.
//                        You will answer questions about Java programming and provide code examples when necessary.
//                        Response in 200 words or less.
//                        Deny the request politely, if it is not related to Java programming.
//                        """)
//                .build();
//    }

    @GetMapping("/ask")
    public String askAI(@RequestParam String message, @RequestParam String conversationId) {
        return chatClient.prompt()
                .advisors(a->a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(message)
                .call().content();
    }
    @GetMapping
    public String chat(@RequestParam String message) {
        return chatClient.prompt()
                .advisors(new TokenUsageCustomAdvisor())
                .user(message)
                .call().content();
    }

}
