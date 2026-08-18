package com.github.sivaone.ai.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.definition.ToolDefinition;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OpenAiChatCompletionTest {

    private ChatClient chatClient;
    private ChatClient.ChatClientRequestSpec requestSpec;
    private ChatClient.CallResponseSpec callResponseSpec;
    private OpenAiChatCompletion openAiChatCompletion;

    @BeforeEach
    void setUp() {
        ChatClient.Builder chatClientBuilder = mock(ChatClient.Builder.class, Answers.RETURNS_SELF);
        chatClient = mock(ChatClient.class);
        requestSpec = mock(ChatClient.ChatClientRequestSpec.class, Answers.RETURNS_SELF);
        callResponseSpec = mock(ChatClient.CallResponseSpec.class);

        when(chatClientBuilder.build()).thenReturn(chatClient);
        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(callResponseSpec);

        openAiChatCompletion = new OpenAiChatCompletion(chatClientBuilder, List.of());
    }

    @Test
    void complete_sendsUserPromptAndReturnsContent() {
        when(callResponseSpec.content()).thenReturn("42");

        String result = openAiChatCompletion.complete("What is the meaning of life?");

        assertThat(result).isEqualTo("42");
        verify(requestSpec).user("What is the meaning of life?");
    }

    @Test
    void completePromptTemplate_populatesTemplateWithTopicParam() {
        when(callResponseSpec.content()).thenReturn("Spring AI is a framework...");
        ChatClient.PromptUserSpec promptUserSpec = mock(ChatClient.PromptUserSpec.class, Answers.RETURNS_SELF);
        doAnswer(invocation -> {
            Consumer<ChatClient.PromptUserSpec> consumer = invocation.getArgument(0);
            consumer.accept(promptUserSpec);
            return requestSpec;
        }).when(requestSpec).user(any(Consumer.class));

        String result = openAiChatCompletion.completePromptTemplate("Spring AI");

        assertThat(result).isEqualTo("Spring AI is a framework...");
        verify(promptUserSpec).text(OpenAiChatCompletion.TEMPLATE);
        verify(promptUserSpec).param("topic", "Spring AI");
    }

    @Test
    void constructor_registersToolCallbackProvidersAsDefaultTools() {
        ToolCallback toolCallback = mock(ToolCallback.class);
        ToolDefinition toolDefinition = mock(ToolDefinition.class);
        when(toolDefinition.name()).thenReturn("getCurrentDateTime");
        when(toolCallback.getToolDefinition()).thenReturn(toolDefinition);

        ToolCallbackProvider provider = mock(ToolCallbackProvider.class);
        when(provider.getToolCallbacks()).thenReturn(new ToolCallback[] { toolCallback });

        ChatClient.Builder builder = mock(ChatClient.Builder.class, Answers.RETURNS_SELF);
        when(builder.build()).thenReturn(mock(ChatClient.class));

        assertThatCode(() -> new OpenAiChatCompletion(builder, List.of(provider))).doesNotThrowAnyException();

        verify(builder).defaultTools(provider);
    }
}
