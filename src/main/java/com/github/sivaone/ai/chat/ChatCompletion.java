package com.github.sivaone.ai.chat;

import com.github.sivaone.ai.tools.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ChatCompletion {

    public static final String TEMPLATE = "Tell me about {topic} in 50 words or less.";

    private final ChatClient chatClient;


    /*
       Note: User ChatClient.Builder to create a ChatClient instance.
       Do not use ChatClient.create(chatModel) or ChatClient.builder(chatModel) for which observability is ignored.
       Use ChatClientBuilderConfigurer to create custom builders.
    */
    public ChatCompletion(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {
        System.out.print("Tools are :");
        String toolNames = Arrays.stream(tools.getToolCallbacks())
                        .map(t -> t.getToolDefinition().name())
                .collect(Collectors.joining(", "));
        System.out.println(toolNames);

        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultTools(tools)
                .build();
    }

    // This function is used to calculate interest using simple interest formula
    public String complete(String prompt) {
        Message systemMessage = new SystemMessage("You are a helpful assistant.");
        Message userMessage = new UserMessage(prompt);

        Prompt customPrompt = Prompt.builder().messages(systemMessage, userMessage).build();

        // chatClient.prompt(customPrompt).call();

        /*        chatClient.prompt()
        .advisors()
        .tools()
        .options()
        .messages()
        .toolContext();*/
        /* chatClient
        .prompt()
        .system("You are a helpful assistant.")
        .user(prompt)
        .call();*/

            // Map response to an entity
            //        MyChatResponse entity =
            // chatClient.prompt().user(prompt).call().entity(MyChatResponse.class);

            // Provider-native Structured output
        /*
        ActorFilms actorFilms = chatClient.prompt()
            .user("Generate the filmography for a random actor.")
            .call()
            .entity(ActorFilms.class, spec -> spec.useProviderStructuredOutput());

            // set option globally via advisors
        ActorFilms actorFilms = chatClient.prompt()
            .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
            .user("Generate the filmography for a random actor.")
            .call()
            .entity(ActorFilms.class);
         */

            // Schema validation with retry
        /*
           ActorFilms actorFilms = chatClient.prompt()
           .user("Generate the filmography for a random actor.")
           .call()
           .entity(ActorFilms.class, spec -> spec.schemaValidation());

           ActorFilms actorFilms = chatClient.prompt()
           .user("Generate the filmography for a random actor.")
           .call()
           .entity(ActorFilms.class, spec -> spec
               .useProviderStructuredOutput()
               .validateSchema());
        */

        ToolCallback[] dateTimeTools = ToolCallbacks.from(new DateTimeTools());
        return chatClient
                .prompt()
                .user(prompt)
//                .tools(new DateTimeTools())
                //                .tools(dateTimeTools)
                .call()
                .content();
    }

    public String completePromptTemplate(String prompt) {
        OpenAiChatOptions options =
                OpenAiChatOptions.builder().model("gpt-5-nano").reasoningEffort("low").build();
        System.out.println("Topic is :" + prompt);
        return chatClient.prompt().user(u -> u.text(TEMPLATE).param("topic", prompt)).call().content();
    }
}
