package com.github.sivaone.ai.api;

import com.github.sivaone.ai.chat.OpenAiChatCompletion;
import com.github.sivaone.ai.chat.MyChatRequest;
import com.github.sivaone.ai.chat.MyChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final OpenAiChatCompletion chatCompletion;

    public ChatController(OpenAiChatCompletion chatCompletion) {
        this.chatCompletion = chatCompletion;
    }

    @PostMapping("/chat")
    public MyChatResponse chat(@RequestBody MyChatRequest request) {
        String complete = chatCompletion.complete(request.message());

        return new MyChatResponse(complete);
    }

    @PostMapping("/chat-template")
    public MyChatResponse chatTemplate(@RequestBody MyChatRequest request) {
        String response = chatCompletion.completePromptTemplate(request.message());
        return new MyChatResponse(response);
    }
}
