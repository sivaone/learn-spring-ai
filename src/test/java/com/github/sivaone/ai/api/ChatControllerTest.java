package com.github.sivaone.ai.api;

import com.github.sivaone.ai.chat.OpenAiChatCompletion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OpenAiChatCompletion chatCompletion;

    @Test
    void chat_returnsResponseFromChatCompletion() throws Exception {
        when(chatCompletion.complete("Hello")).thenReturn("Hi there!");

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"message":"Hello"}"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Hi there!"));

        verify(chatCompletion).complete("Hello");
    }

    @Test
    void chatTemplate_returnsTemplatedResponse() throws Exception {
        when(chatCompletion.completePromptTemplate("Spring AI")).thenReturn("Spring AI is great");

        mockMvc.perform(post("/api/chat-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"message":"Spring AI"}"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Spring AI is great"));

        verify(chatCompletion).completePromptTemplate("Spring AI");
    }
}
