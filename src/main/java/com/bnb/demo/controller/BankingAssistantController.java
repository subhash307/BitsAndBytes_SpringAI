package com.bnb.demo.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BankingAssistantController {

    private ChatClient chatClient;

    BankingAssistantController(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    @Value("classpath:/prompts/promptStuffing.st")
    Resource bankingAssistantPrompt;

    @GetMapping("/stuffing")
    public String promptStuffing(@RequestParam String message) {
        return chatClient
                .prompt()
                .system(bankingAssistantPrompt)
                .user(message)
                .call()
                .content();
    }
}
