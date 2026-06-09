package com.github.sivaone.ai.api;

import com.github.sivaone.ai.chat.ChatCompletion;
import com.github.sivaone.ai.chat.MyChatRequest;
import com.github.sivaone.ai.chat.MyChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatCompletion chatCompletion;

    public ChatController(ChatCompletion chatCompletion) {
        this.chatCompletion = chatCompletion;
    }

    @PostMapping
    public MyChatResponse chat(@RequestBody MyChatRequest request) {
        String complete = chatCompletion.complete(request.message());

        //    String complete = chatCompletion.completePromptTemplate(request.message());
        return new MyChatResponse(complete);
    }
}
