package com.github.sivaone.ai.chat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.sivaone.ai.api.ChatController;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ChatControllerTest {

    private ChatCompletion chatCompletion;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        chatCompletion = mock(ChatCompletion.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ChatController(chatCompletion))
                .build();
    }

    // @Test
    void chat_returnsResponseFromService() throws Exception {
        when(chatCompletion.complete("hello")).thenReturn("world");

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"hello\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("world"));
    }
}
