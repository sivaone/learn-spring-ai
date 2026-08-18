package com.github.sivaone.ai.rag;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStoreRetriever;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RagServiceTest {

    private final VectorStoreRetriever retriever = mock(VectorStoreRetriever.class);

    private final ChatModel chatModel = mock(ChatModel.class);

    private final RagService ragService = new RagService(retriever, chatModel);

    @Test
    void generateResponse_buildsPromptFromRetrievedDocumentsAndReturnsChatModelResponse() {
        SearchRequest expectedRequest = SearchRequest.builder()
                .query("What is Spring AI?")
                .topK(3)
                .similarityThreshold(0.5)
                .build();
        Document doc1 = new Document("Spring AI simplifies building AI applications.");
        Document doc2 = new Document("It provides abstractions over LLM providers.");
        when(retriever.similaritySearch(eq(expectedRequest))).thenReturn(List.of(doc1, doc2));
        when(chatModel.call(
                "Context information:\nSpring AI simplifies building AI applications.\n\n"
                        + "It provides abstractions over LLM providers.\n\nUser query: What is Spring AI?"))
                .thenReturn("Spring AI is a framework for building AI applications.");

        String response = ragService.generateResponse("What is Spring AI?");

        assertThat(response).isEqualTo("Spring AI is a framework for building AI applications.");
    }

    @Test
    void generateResponse_withNoRelevantDocuments_stillQueriesChatModelWithEmptyContext() {
        when(retriever.similaritySearch(eq(SearchRequest.builder()
                .query("Unrelated query")
                .topK(3)
                .similarityThreshold(0.5)
                .build()))).thenReturn(List.of());
        when(chatModel.call("Context information:\n\n\nUser query: Unrelated query"))
                .thenReturn("I don't have enough information to answer that.");

        String response = ragService.generateResponse("Unrelated query");

        assertThat(response).isEqualTo("I don't have enough information to answer that.");
        verify(chatModel).call("Context information:\n\n\nUser query: Unrelated query");
    }
}
