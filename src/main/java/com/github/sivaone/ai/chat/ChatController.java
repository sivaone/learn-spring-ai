package com.github.sivaone.ai.chat;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final OpenAiChatCompletion chatCompletion;

  public ChatController(OpenAiChatCompletion chatCompletion) {
    this.chatCompletion = chatCompletion;
  }

  @PostMapping
  public ChatResponse chat(@RequestBody ChatRequest request) {
    return new ChatResponse(chatCompletion.complete(request.message()));
  }
}
