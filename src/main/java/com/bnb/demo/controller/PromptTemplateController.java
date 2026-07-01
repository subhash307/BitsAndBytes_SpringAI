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
public class PromptTemplateController {
    private ChatClient chatClient;

    PromptTemplateController(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }


    @Value("classpath:/prompts/promptTemplate.st")
    Resource promptTemplate;

    @GetMapping("/email")
    public String emailResponse(
            @RequestParam String customerName,
            @RequestParam String message) {
        return chatClient
                .prompt()
                .system("You are a customer support agent. " +
                        "You will respond to customer emails in a professional and helpful manner.")
                .user(prompt ->
                        prompt.text(promptTemplate)
                                .param("customerName", customerName)
                                .param("message", message))
                .call()
                .content();
    }
}
