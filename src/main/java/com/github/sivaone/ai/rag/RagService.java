package com.github.sivaone.ai.rag;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStoreRetriever;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RagService {

    private final VectorStoreRetriever retriever;
    // This is just for learning. In prod, use ChatClient.Builder to create a ChatClient instance.
    private final ChatModel chatModel;

    public RagService(VectorStoreRetriever retriever, ChatModel chatModel) {
        this.retriever = retriever;
        this.chatModel = chatModel;
    }

    public String generateResponse(String query) {
        // Retrieve relevant documents
        List<Document> relevantDocs = retriever.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .similarityThreshold(0.5)
                        .build()
        );

        // Extract content from documents to use as context
        String context = relevantDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        // Generate response using the retrieved context
        String prompt = "Context information:\n" + context + "\n\nUser query: " + query;
        log.info("Prompt: {}", prompt);
        return chatModel.call(prompt);
    }
}
