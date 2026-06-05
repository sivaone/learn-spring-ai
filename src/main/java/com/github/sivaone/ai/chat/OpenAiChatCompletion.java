package com.github.sivaone.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAiChatCompletion {

  private final ChatClient chatClient;

  public OpenAiChatCompletion(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  public String complete(String prompt) {
    return chatClient.prompt().user(prompt).call().content();
  }
}
