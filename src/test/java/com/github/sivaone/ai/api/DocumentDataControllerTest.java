package com.github.sivaone.ai.api;

import com.github.sivaone.ai.vector.DocumentDataService;
import com.github.sivaone.ai.vector.MyDocument;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentDataController.class)
class DocumentDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DocumentDataService documentDataService;

    @Test
    void saveDocument_returnsCreated() throws Exception {
        mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text":"Spring AI notes","metadata":{"platform":"Java"}}"""))
                .andExpect(status().isCreated());

        verify(documentDataService).saveDocument(new MyDocument("Spring AI notes", Map.of("platform", "Java")));
    }

    @Test
    void getDocuments_returnsMatchingDocuments() throws Exception {
        Document document = new Document("Spring AI notes", Map.of("platform", "Java"));
        when(documentDataService.retrieveDocuments("Spring AI")).thenReturn(List.of(document));

        mockMvc.perform(get("/api/documents/search").param("query", "Spring AI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Spring AI notes"));

        verify(documentDataService).retrieveDocuments("Spring AI");
    }
}
