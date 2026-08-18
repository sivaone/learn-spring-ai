package com.github.sivaone.ai.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OpenAiChatCompletion {

    public static final String TEMPLATE = "Tell me about {topic} in 50 words or less.";

    private final ChatClient chatClient;

    /*
       Note: Use ChatClient.Builder to create a ChatClient instance.
       Do not use ChatClient.create(chatModel) or ChatClient.builder(chatModel) for which observability is ignored.
       Use ChatClientBuilderConfigurer to create custom builders.
    */
    public OpenAiChatCompletion(
            ChatClient.Builder chatClientBuilder,
            List<ToolCallbackProvider> toolCallbackProviders
    ) {
        String toolNames = toolCallbackProviders.stream()
                .flatMap(provider -> Arrays.stream(provider.getToolCallbacks()))
                .map(t -> t.getToolDefinition().name())
                .collect(Collectors.joining(", "));
        log.info("Tools are :{}", toolNames);

        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultTools(toolCallbackProviders.toArray())
                .build();
    }

    public String complete(String prompt) {
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

    public String completePromptTemplate(String prompt) {
        log.info("Topic is :{}", prompt);

        return chatClient.prompt()
                .user(u -> u.text(TEMPLATE).param("topic", prompt))
                .call()
                .content();
    }
}
