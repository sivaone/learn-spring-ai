package com.github.sivaone.ai.api;

import com.github.sivaone.ai.rag.RagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RagController.class)
class RagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RagService ragService;

    @Test
    void generate_returnsRagServiceResponse() throws Exception {
        when(ragService.generateResponse("What is Spring AI?")).thenReturn("Spring AI is a framework...");

        mockMvc.perform(get("/api/rag/generate").param("prompt", "What is Spring AI?"))
                .andExpect(status().isOk())
                .andExpect(content().string("Spring AI is a framework..."));

        verify(ragService).generateResponse("What is Spring AI?");
    }
}
