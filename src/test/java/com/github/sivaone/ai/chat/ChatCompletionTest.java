package com.github.sivaone.ai.chat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.ai.chat.client.ChatClient;

class ChatCompletionTest {

    private ChatClient chatClient;
    private ChatCompletion service;

    @BeforeEach
    void setUp() {
        chatClient = mock(ChatClient.class);
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        when(builder.build()).thenReturn(chatClient);
        // service = new ChatCompletion(builder);
    }

    // @Test
    void complete_returnsContentFromChatClient() {
        var requestSpec = mock(ChatClient.ChatClientRequestSpec.class);
        var callSpec = mock(ChatClient.CallResponseSpec.class);

        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.user("hello")).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(callSpec);
        when(callSpec.content()).thenReturn("world");

        assertThat(service.complete("hello")).isEqualTo("world");
    }
}
