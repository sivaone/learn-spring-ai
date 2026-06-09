package com.github.sivaone.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean("defaultChatClient")
    ChatClient defaultChatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("You are a helpful assistant.")
                //                .defaultAdvisors()
                //                .defaultTools()
                //                .defaultOptions()
                //                .defaultToolContext()
                //                .defaultModel("gpt-5-nano")
                .build();
    }

    /*@Bean
    @Primary
    public ChatClient openAiChatClient(OpenAiChatModel chatModel, ChatClientBuilderConfigurer configurer,
                                       ObjectProvider<ObservationRegistry> observationRegistry,
                                       ObjectProvider<ChatClientObservationConvention> chatClientObservationConvention,
                                       ObjectProvider<AdvisorObservationConvention> advisorObservationConvention,
                                       ObjectProvider<ToolCallingAdvisor.Builder<?>> toolCallingAdvisorBuilder) {
        return buildChatClient(chatModel, configurer, observationRegistry,
                chatClientObservationConvention, advisorObservationConvention, toolCallingAdvisorBuilder);
    }

    @Bean
    public ChatClient anthropicChatClient(AnthropicChatModel chatModel, ChatClientBuilderConfigurer configurer,
                                          ObjectProvider<ObservationRegistry> observationRegistry,
                                          ObjectProvider<ChatClientObservationConvention> chatClientObservationConvention,
                                          ObjectProvider<AdvisorObservationConvention> advisorObservationConvention,
                                          ObjectProvider<ToolCallingAdvisor.Builder<?>> toolCallingAdvisorBuilder) {
        return buildChatClient(chatModel, configurer, observationRegistry,
                chatClientObservationConvention, advisorObservationConvention, toolCallingAdvisorBuilder);
    }

    private ChatClient buildChatClient(ChatModel chatModel, ChatClientBuilderConfigurer configurer,
                                       ObjectProvider<ObservationRegistry> observationRegistry,
                                       ObjectProvider<ChatClientObservationConvention> chatClientObservationConvention,
                                       ObjectProvider<AdvisorObservationConvention> advisorObservationConvention,
                                       ObjectProvider<ToolCallingAdvisor.Builder<?>> toolCallingAdvisorBuilder) {
        ChatClient.Builder builder = ChatClient.builder(chatModel,
                observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP),
                chatClientObservationConvention.getIfUnique(),
                advisorObservationConvention.getIfUnique(),
                toolCallingAdvisorBuilder.getIfAvailable());
        return configurer.configure(builder).build();
    }*/
}
