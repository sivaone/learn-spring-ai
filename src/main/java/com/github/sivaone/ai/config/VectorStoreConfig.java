package com.github.sivaone.ai.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    /*@Bean
    public EmbeddingModel embeddingModel() {
        return new OpenAiEmbeddingModel(
                OpenAiEmbeddingOptions.builder()
                        .apiKey(System.getenv("OPENAI_API_KEY"))
                        .model("text‑embedding‑3‑small")
                        .build()
        );
    }*/
}
